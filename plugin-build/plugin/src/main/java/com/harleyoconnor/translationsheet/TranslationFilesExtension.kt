package com.harleyoconnor.translationsheet

import com.harleyoconnor.translationsheet.generation.format.*
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.Property
import javax.inject.Inject

const val DEFAULT_CREDENTIALS_FILE = "credentials.json"
const val DEFAULT_TOKENS_DIR = "tokens"

@Suppress("UnnecessaryAbstractClass", "UNCHECKED_CAST")
abstract class TranslationFilesExtension @Inject constructor(project: Project) {

    private val objects = project.objects

    val credentialsFile: RegularFileProperty = objects.fileProperty().convention(
        project.layout.buildDirectory.file(DEFAULT_CREDENTIALS_FILE)
    )

    val tokensDirectory: DirectoryProperty = objects.directoryProperty().convention(
        project.layout.buildDirectory.dir(DEFAULT_TOKENS_DIR)
    )

    val sheetId: Property<String> = objects.property(String::class.java)

    val outputDirectory: DirectoryProperty = objects.directoryProperty()

    var configuredFormat: ConfiguredFormat<*, *> = ConfiguredFormat(Json, JsonFormattingConfig())

    fun useJson() {
        configuredFormat = ConfiguredFormat(Json, JsonFormattingConfig());
    }

    fun useJson(action: Action<JsonFormattingConfig>) {
        val format = JsonFormattingConfig()
        action.execute(format)
        configuredFormat = ConfiguredFormat(Json, format)
    }

    fun useLang() {
        configuredFormat = ConfiguredFormat(Lang, EmptyFormattingConfig)
    }

}
