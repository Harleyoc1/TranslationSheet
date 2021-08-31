package com.harleyoconnor.translationsheet

import com.google.api.services.sheets.v4.model.Color
import com.harleyoconnor.translationsheet.extension.toLong
import org.junit.Test

class TranslationSheetPluginTest {

    companion object {
        const val ID = "com.harleyoconnor.translationsheet"
    }

    @Test
    fun `test Color#toInt()`() {
        assert(Color().setRed(249F / 256F).setGreen(203F / 256F).setBlue(156F / 256F).setAlpha(256F).toLong()
                == 4190870783)
    }

//    @Test
//    fun `plugin is applied correctly to the project`() {
//        val project = ProjectBuilder.builder().build()
//        project.pluginManager.apply(ID)
//
//        assert(project.tasks.getByName(TASK_NAME) is GenerateFilesTask)
//    }
//
//    @Test
//    fun `extension translationFilesGeneration is created correctly`() {
//        val project = ProjectBuilder.builder().build()
//        project.pluginManager.apply(ID)
//
//        assertNotNull(project.extensions.getByName(EXTENSION_NAME))
//    }
//
//    @Test
//    fun `parameters are passed correctly from extension to task`() {
//        val project = ProjectBuilder.builder().build()
//        project.pluginManager.apply(ID)
//        (project.extensions.getByName(EXTENSION_NAME) as TranslationFilesExtension).apply {
//            sheetId.set("sheetid")
//            outputDirectory.set(project.projectDir)
//        }
//
//        val task = project.tasks.getByName(TASK_NAME) as GenerateFilesTask
//
//        assertEquals("sheetid", task.sheetId.get())
//        assertEquals(project.projectDir, task.outputDirectory.get().asFile)
//    }

}
