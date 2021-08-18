package com.harleyoconnor.translationsheet

import com.google.api.services.sheets.v4.Sheets
import com.google.api.services.sheets.v4.model.CellData
import com.google.api.services.sheets.v4.model.CellFormat
import com.google.api.services.sheets.v4.model.Color
import com.google.api.services.sheets.v4.model.ExtendedValue
import com.google.api.services.sheets.v4.model.GridData
import com.google.api.services.sheets.v4.model.RowData
import com.google.api.services.sheets.v4.model.Sheet
import com.google.api.services.sheets.v4.model.Spreadsheet
import com.harleyoconnor.translationsheet.extension.equals
import com.harleyoconnor.translationsheet.extension.fillTo
import com.harleyoconnor.translationsheet.extension.getAsList
import com.harleyoconnor.translationsheet.extension.removeIf
import com.harleyoconnor.translationsheet.generation.format.ConfiguredFormat
import com.harleyoconnor.translationsheet.generation.format.Format
import com.harleyoconnor.translationsheet.generation.format.FormattingConfig
import com.harleyoconnor.translationsheet.util.getSheetsService
import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.options.Option
import java.util.regex.Pattern

abstract class GenerateFilesTask : DefaultTask() {

    companion object {
        val LANGUAGE_ID_PATTERN: Pattern = Pattern.compile("[a-z]{2}_[a-z]{2}")
        private const val ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    }

    init {
        description = "Generates translation files from Google Sheets document."
    }

    @get:InputFile
    @get:Option(option = "credentialsFile", description = "The Json file the OAuth credentials are stored in.")
    abstract val credentialsFile: RegularFileProperty

    @get:InputDirectory
    @get:Option(option = "tokensDirectory", description = "The directory to store tokens in.")
    abstract val tokensDirectory: DirectoryProperty

    @get:Input
    @get:Option(option = "sheetId", description = "The ID of the spreadsheet to generate from.")
    abstract val sheetId: Property<String>

    @get:Input
    abstract var configuredFormat: ConfiguredFormat<Format, FormattingConfig>

    @get:OutputDirectory
    @get:Option(option = "outputDirectory", description = "The directory to output the generated language files to.")
    abstract val outputDirectory: DirectoryProperty

    @get:Input
    @get:Option(option = "sectionPattern", description = "The pattern to match the section name against.")
    abstract val sectionPattern: Property<String>

    @TaskAction
    fun execute() {
        val sheetsService = getSheetsService(this.credentialsFile.asFile.get(), this.tokensDirectory.get())

        // Gather the language IDs.
        val languageIds = sheetsService
            .getAsList(this.sheetId.get(), "2:2")
            .filter { LANGUAGE_ID_PATTERN.matcher(it).matches() }
        // Gather data about the section.
        val sectionData = this.getSectionData(sheetsService)
        // Gather the translation keys.
        val keys = sheetsService.getAsList(this.sheetId.get(), "A${sectionData.startRow}:A${sectionData.endRow}")

        var column = 3
        languageIds.forEach { languageId ->
            this.generate(languageId, this.getTranslationMap(sheetsService, this.asColumn(column++), sectionData, keys))
        }
    }

    private fun getTranslationMap(
        sheetsService: Sheets,
        column: String,
        sectionData: SectionData,
        keys: MutableList<String>
    ): MutableMap<String, String> {
        val values = sheetsService
            .getAsList(this.sheetId.get(), "$column${sectionData.startRow}:$column${sectionData.endRow}")
            .fillTo((sectionData.endRow - sectionData.startRow) + 1, "")
        var i = 0

        return mutableMapOf(
            *keys.stream()
                .map { it to values[i++] }
                .toList()
                .toTypedArray()
        ).removeIf { _, value ->
            value.isEmpty()
        }
    }

    private data class SectionData(
        val startRow: Int,
        val endRow: Int
    )

    private fun getSectionData(sheetsService: Sheets): SectionData {
        val sections = this.getSections(sheetsService)
        val sectionPattern = Pattern.compile(this.sectionPattern.get())

        val section = sections.entries.stream()
            .filter { sectionPattern.matcher(it.key).matches() }
            .findFirst()
            .orElseThrow {
                RuntimeException(
                    "Could not find section for pattern `" + this.sectionPattern.get() + "`. Make sure you have " +
                        "a section that matches this pattern."
                )
            }

        val keyList = sections.keys.toList()
        return SectionData(
            section.value + 1,
            sections[keyList[keyList.indexOf(section.key) + 1]]!! - 1
        )
    }

    private fun generate(
        languageId: String,
        translationMap: MutableMap<String, String>
    ) {
        configuredFormat.generator.generate(
            configuredFormat.config,
            outputDirectory.file(
                "$languageId." + (
                    configuredFormat.config.extension
                        ?: configuredFormat.format.getDefaultExtension()
                    )
            ).get().asFile,
            translationMap
        )
    }

    private fun asColumn(i: Int): String {
        // TODO: Make this work for indices greater than 26.
        return ALPHABET[i - 1].toString()
    }

    @Suppress("UNCHECKED_CAST")
    private fun getSections(sheetsService: Sheets): Map<String, Int> {
        val keyColumn = this.readKeyColumn(sheetsService)
        var i = 1
        val separators: MutableMap<String, Int> = mutableMapOf()

        val gridData = (keyColumn["sheets"] as ArrayList<Sheet>)[0]["data"] as ArrayList<GridData>
        (gridData[0]["rowData"] as ArrayList<RowData>)
            .forEach {
                val cellData = (it["values"] as ArrayList<CellData>)[0]
                if (((cellData["effectiveFormat"] as CellFormat)["backgroundColor"] as Color)
                    .equals(249F / 256F, 203F / 256F, 156F / 256F)
                ) {
                    separators[(cellData["effectiveValue"] as ExtendedValue).stringValue] = i
                }
                i++
            }

        return separators
    }

    private fun readKeyColumn(sheetsService: Sheets): Spreadsheet {
        return sheetsService.spreadsheets()
            .get(this.sheetId.get())
            .setIncludeGridData(true)
            .setRanges(mutableListOf("A:A"))
            .execute()
    }

}
