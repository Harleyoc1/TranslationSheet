package com.harleyoconnor.translationsheet

import com.harleyoconnor.translationsheet.generation.format.ConfiguredFormat
import com.harleyoconnor.translationsheet.generation.format.Format
import com.harleyoconnor.translationsheet.generation.format.FormattingConfig
import com.harleyoconnor.translationsheet.generation.format.getGenerator
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

abstract class GenerateFilesTask: DefaultTask() {

    init {
        description = "Generates translation files from Google Sheets document."

        // Don't forget to set the group here.
        // group = BasePlugin.BUILD_GROUP
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
    abstract val outputDirectory: DirectoryProperty

    @TaskAction
    fun <F: Format> execute() {
//        val httpTransport = GoogleNetHttpTransport.newTrustedTransport()
//        val credentials = credentials(this.credentialsFile.asFile.get(), this.tokensDirectory.get(), httpTransport)

        val english = mapOf("example.key" to "Example Value")

        getGenerator(configuredFormat).generate(
            configuredFormat.config,
            outputDirectory.file("en_us." + (this.configuredFormat.config.extension
                ?: this.configuredFormat.format.getDefaultExtension())).get().asFile,
            english
        )

    }

}
