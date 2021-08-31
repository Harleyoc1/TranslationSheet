package com.harleyoconnor.translationsheet

import com.harleyoconnor.translationsheet.extension.mkdirs
import com.harleyoconnor.translationsheet.generation.FileGenerator
import com.harleyoconnor.translationsheet.generation.JsonFileGenerator
import com.harleyoconnor.translationsheet.generation.LangFileGenerator
import com.harleyoconnor.translationsheet.generation.format.ConfiguredFormat
import com.harleyoconnor.translationsheet.generation.format.EmptyFormattingConfig
import com.harleyoconnor.translationsheet.generation.format.Json
import com.harleyoconnor.translationsheet.generation.format.JsonFormattingConfig
import com.harleyoconnor.translationsheet.generation.format.Lang
import com.harleyoconnor.translationsheet.util.SerializableTriConsumer
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.Property
import java.io.File
import javax.inject.Inject

const val DEFAULT_CREDENTIALS_FILE = "credentials.json"
const val DEFAULT_TOKENS_DIR = "tokens"
const val DEFAULT_PRIMARY_LANG = "en_us"

@Suppress("UnnecessaryAbstractClass", "UNCHECKED_CAST")
abstract class TranslationFilesExtension @Inject constructor(private val project: Project) {

    private val objects = project.objects

    /**
     * The file the OAuth credentials are stored in.
     *
     * Conventionally stored in `build/credentials.json`. This should be ignored by Git to avoid uploading sensitive
     * data to an online repository hosting platform.
     *
     * @see <a href="https://developers.google.com/workspace/guides/create-credentials">Guide to creating OAuth
     * credentials</a>
     */
    val credentialsFile: RegularFileProperty = objects.fileProperty().convention(
        project.layout.buildDirectory.file(DEFAULT_CREDENTIALS_FILE)
    )

    /**
     * The directory to store generated access tokens in.
     *
     * Conventionally `build/tokens`. This should be ignored by Git to avoid uploading sensitive data to an online
     * repository hosting platform.
     */
    val tokensDirectory: DirectoryProperty = objects.directoryProperty().convention(
        project.layout.buildDirectory.dir(DEFAULT_TOKENS_DIR)
    )

    /**
     * The ID of the spreadsheet to generate from.
     *
     * This can be found in the URL of the sheet, which is usually in the form
     * `https://docs.google.com/spreadsheets/d/<sheetId>/`.
     */
    val sheetId: Property<String> = objects.property(String::class.java)

    /**
     * The primary language. This language will not have a file generated and will be used for updating the sheet with
     * new translation keys and their primary language value (coming soon).
     */
    val primaryLang: Property<String> = objects.property(String::class.java).convention(DEFAULT_PRIMARY_LANG)

    /**
     * The directory to output the generated language files to.
     */
    val outputDirectory: DirectoryProperty = objects.directoryProperty()

    /**
     * The colour of the section separator cells.
     *
     * A sheet may use separators in order to store translations for multiple projects. There must be at least one
     * section that matches this colour and the specified [sectionPattern] in the sheet to know where to obtain the
     * keys and their values to generate.
     */
    val sectionColour: Property<Long> = objects.property(Long::class.java)

    /**
     * The pattern to match the section name against.
     *
     * @see [sectionColour]
     */
    val sectionPattern: Property<String> = objects.property(String::class.java)

    /**
     * Creates directory at given [path] if it does not already exist, setting the resulting
     * directory to the [outputDirectory].
     *
     * @param path The path of the [outputDirectory], relative to the project directory.
     */
    fun outputDir(path: String) {
        this.outputDirectory.set(project.layout.projectDirectory.dir(path).mkdirs())
    }

    /**
     * The configured format to generate by.
     *
     * @see [useJson]
     * @see [useLang]
     */
    var configuredFormat: ConfiguredFormat<*, *> = ConfiguredFormat(Json, JsonFormattingConfig(), JsonFileGenerator)

    /**
     * Sets up Json defaults as the [configuredFormat] to generate by.
     */
    fun useJson() {
        configuredFormat = ConfiguredFormat(Json, JsonFormattingConfig(), JsonFileGenerator)
    }

    /**
     * Sets up Json as the [configuredFormat] to generate by, customising the formatting config using the specified
     * [action].
     */
    fun useJson(action: Action<JsonFormattingConfig>) {
        val format = JsonFormattingConfig()
        action.execute(format)
        configuredFormat = ConfiguredFormat(Json, format, JsonFileGenerator)
    }

    /**
     * Sets up Json as the [configuredFormat] to generate by with the specified [generator], customising the formatting
     * config using the specified [action].
     */
    fun useJson(
        action: Action<JsonFormattingConfig>,
        generator: SerializableTriConsumer<JsonFormattingConfig, File, Map<String, String>>
    ) {
        val format = JsonFormattingConfig()
        action.execute(format)

        configuredFormat = ConfiguredFormat.create(Json, format) { config, outputFile, translationMap ->
            generator.accept(config, outputFile, translationMap)
        }
    }

    /**
     * Sets up Lang defaults as the [configuredFormat] to generate by.
     */
    fun useLang() {
        this.useLang(LangFileGenerator)
    }

    /**
     * Sets up Lang as the [configuredFormat] to generate by with the specified [generator].
     */
    fun useLang(generator: FileGenerator<EmptyFormattingConfig>) {
        configuredFormat = ConfiguredFormat(Lang, EmptyFormattingConfig, generator)
    }

}
