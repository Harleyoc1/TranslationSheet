package com.harleyoconnor.translationsheet

import org.gradle.testfixtures.ProjectBuilder
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test

class TranslationSheetPluginTest {

    companion object {
        const val ID = "com.harleyoconnor.translationsheet"
    }

    @Test
    fun `plugin is applied correctly to the project`() {
        val project = ProjectBuilder.builder().build()
        project.pluginManager.apply(ID)

        assert(project.tasks.getByName(TASK_NAME) is GenerateFilesTask)
    }

    @Test
    fun `extension translationFilesGeneration is created correctly`() {
        val project = ProjectBuilder.builder().build()
        project.pluginManager.apply(ID)

        assertNotNull(project.extensions.getByName(EXTENSION_NAME))
    }

    @Test
    fun `parameters are passed correctly from extension to task`() {
        val project = ProjectBuilder.builder().build()
        project.pluginManager.apply(ID)
        (project.extensions.getByName(EXTENSION_NAME) as TranslationFilesExtension).apply {
            sheetId.set("sheetid")
            outputDirectory.set(project.projectDir)
        }

        val task = project.tasks.getByName(TASK_NAME) as GenerateFilesTask

        assertEquals("sheetid", task.sheetId.get())
        assertEquals(project.projectDir, task.outputDirectory.get().asFile)
    }

}
