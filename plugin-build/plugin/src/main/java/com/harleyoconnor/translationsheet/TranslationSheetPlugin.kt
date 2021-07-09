package com.harleyoconnor.translationsheet

import com.harleyoconnor.translationsheet.extension.createDirs
import com.harleyoconnor.translationsheet.extension.createFile
import com.harleyoconnor.translationsheet.generation.format.ConfiguredFormat
import com.harleyoconnor.translationsheet.generation.format.Format
import com.harleyoconnor.translationsheet.generation.format.FormattingConfig
import org.gradle.api.Plugin
import org.gradle.api.Project

const val EXTENSION_NAME = "translationFilesGeneration"
const val TASK_NAME = "generateTranslationFiles"

val CONFIGURED_FORMATS = mutableListOf<ConfiguredFormat<Format, FormattingConfig>>()

@Suppress("UNCHECKED_CAST")
abstract class TranslationSheetPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        // Add the 'template' extension object
        val extension = project.extensions.create(EXTENSION_NAME, TranslationFilesExtension::class.java, project)

        // Add a task that uses configuration from the extension object
        project.tasks.register(TASK_NAME, GenerateFilesTask::class.java) {
            CONFIGURED_FORMATS.add(extension.configuredFormat as ConfiguredFormat<Format, FormattingConfig>)

            it.credentialsFile.set(extension.credentialsFile.createFile())
            it.tokensDirectory.set(extension.tokensDirectory.createDirs())
            it.sheetId.set(extension.sheetId)
            it.outputDirectory.set(extension.outputDirectory.createDirs())
            it.configuredFormatIndex.set(CONFIGURED_FORMATS.size - 1)
        }
    }

}
