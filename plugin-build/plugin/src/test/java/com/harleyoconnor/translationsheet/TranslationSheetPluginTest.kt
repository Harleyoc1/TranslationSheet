package com.harleyoconnor.translationsheet

import org.gradle.testfixtures.ProjectBuilder
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import java.io.File

class TranslationSheetPluginTest {

    companion object {
        const val ID = "com.harleyoconnor.translationsheet"
        const val EXTENSION_NAME = "translationFilesGeneration"
        const val TASK_NAME = "generateTranslationFiles"
    }

    @Test
    fun `plugin is applied correctly to the project`() {
        val project = ProjectBuilder.builder().build()
        project.pluginManager.apply(ID)

        assert(project.tasks.getByName(TASK_NAME) is GenerateFilesTask)
    }

    @Test
    fun `extension templateExampleConfig is created correctly`() {
        val project = ProjectBuilder.builder().build()
        project.pluginManager.apply(ID)

        assertNotNull(project.extensions.getByName(EXTENSION_NAME))
    }

    @Test
    fun `parameters are passed correctly from extension to task`() {
        val project = ProjectBuilder.builder().build()
        project.pluginManager.apply(ID)
        val aFile = File(project.projectDir, ".tmp")
        (project.extensions.getByName(EXTENSION_NAME) as TemplateExtension).apply {
            tag.set("a-sample-tag")
            message.set("just-a-message")
            outputFile.set(aFile)
        }

        val task = project.tasks.getByName(TASK_NAME) as GenerateFilesTask

        assertEquals("a-sample-tag", task.tag.get())
        assertEquals("just-a-message", task.message.get())
        assertEquals(aFile, task.outputFile.get().asFile)
    }

}
