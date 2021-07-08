package com.harleyoconnor.translationsheet

import com.harleyoconnor.translationsheet.extension.mkdirs
import com.harleyoconnor.translationsheet.generation.format.ConfiguredFormat
import com.harleyoconnor.translationsheet.generation.format.Format
import com.harleyoconnor.translationsheet.generation.format.FormattingConfig
import org.gradle.api.Plugin
import org.gradle.api.Project

const val EXTENSION_NAME = "translationFilesGeneration"
const val TASK_NAME = "generateTranslationFiles"

@Suppress("UNCHECKED_CAST")
abstract class TranslationSheetPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        // Add the 'template' extension object
        val extension = project.extensions.create(EXTENSION_NAME, TranslationFilesExtension::class.java, project)

        // Add a task that uses configuration from the extension object
        project.tasks.register(TASK_NAME, GenerateFilesTask::class.java) {
            // If directories don't exist, create them.
            extension.tokensDirectory.orNull?.mkdirs()
            extension.outputDirectory.orNull?.mkdirs()

            it.credentialsFile.set(extension.credentialsFile)
            it.tokensDirectory.set(extension.tokensDirectory)
            it.sheetId.set(extension.sheetId)
            it.outputDirectory.set(extension.outputDirectory)
            it.configuredFormat = extension.configuredFormat as ConfiguredFormat<Format, FormattingConfig>
        }
    }

}
