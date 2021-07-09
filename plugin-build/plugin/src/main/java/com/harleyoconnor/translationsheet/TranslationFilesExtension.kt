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
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.Property
import org.gradle.internal.TriAction
import java.io.File
import javax.inject.Inject

const val DEFAULT_CREDENTIALS_FILE = "credentials.json"
const val DEFAULT_TOKENS_DIR = "tokens"

@Suppress("UnnecessaryAbstractClass", "UNCHECKED_CAST")
abstract class TranslationFilesExtension @Inject constructor(private val project: Project) {

    private val objects = project.objects

    val credentialsFile: RegularFileProperty = objects.fileProperty().convention(
        project.layout.buildDirectory.file(DEFAULT_CREDENTIALS_FILE)
    )

    val tokensDirectory: DirectoryProperty = objects.directoryProperty().convention(
        project.layout.buildDirectory.dir(DEFAULT_TOKENS_DIR)
    )

    val sheetId: Property<String> = objects.property(String::class.java)

    val outputDirectory: DirectoryProperty = objects.directoryProperty()

    /**
     * Creates directory at given [path] if it does not already exist, setting the resulting
     * directory to the [outputDirectory].
     *
     * @param path The path of the [outputDirectory], relative to the project directory.
     */
    fun outputDir(path: String) {
        this.outputDirectory.set(project.layout.projectDirectory.dir(path).mkdirs())
    }

    var configuredFormat: ConfiguredFormat<*, *> = ConfiguredFormat(Json, JsonFormattingConfig(), JsonFileGenerator)

    fun useJson() {
        configuredFormat = ConfiguredFormat(Json, JsonFormattingConfig(), JsonFileGenerator)
    }

    fun useJson(action: Action<JsonFormattingConfig>) {
        this.useJson(action) { config, outputFile, translationMap ->
            JsonFileGenerator.generate(config, outputFile, translationMap)
        }
    }

    fun useJson(
        action: Action<JsonFormattingConfig>,
        generator: TriAction<JsonFormattingConfig, File, Map<String, String>>
    ) {
        val format = JsonFormattingConfig()
        action.execute(format)

        configuredFormat = ConfiguredFormat.create(Json, format) { config, outputFile, translationMap ->
            generator.execute(config, outputFile, translationMap)
        }
    }

    fun useLang() {
        this.useLang(LangFileGenerator)
    }

    fun useLang(generator: FileGenerator<EmptyFormattingConfig>) {
        configuredFormat = ConfiguredFormat(Lang, EmptyFormattingConfig, generator)
    }

}
