package com.harleyoconnor.translationsheet

import com.harleyoconnor.translationsheet.generation.LangFileGenerator
import com.harleyoconnor.translationsheet.util.Column
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import java.io.File

class TranslationSheetPluginTest {

    companion object {
        const val ID = "com.harleyoconnor.translationsheet"
    }

    @Test
    fun `plugin is applied correctly to the project`() {
        val project = ProjectBuilder.builder().build()
        project.pluginManager.apply(ID)

        // Set placeholder values to prevent MissingValueException
        (project.extensions.getByName(EXTENSION_NAME) as TranslationFilesExtension).apply {
            sectionColour.set(0L)
            sectionPattern.set("")
        }

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
            credentialsFile.set(File(project.projectDir, ".credentials.tmp"))
            tokensDirectory.set(File(project.projectDir, "tokens"))
            sheetId.set("sheet_id")
            primaryLang.set("primary_lang")
            outputDirectory.set(File(project.projectDir, "output"))
            sectionColour.set(0x000000)
            sectionPattern.set("section_pattern")
            useLang()
        }

        val task = project.tasks.getByName(TASK_NAME) as GenerateFilesTask

        assertEquals(File(project.projectDir, ".credentials.tmp"), task.credentialsFile.get().asFile)
        assertEquals(File(project.projectDir, "tokens"), task.tokensDirectory.get().asFile)
        assertEquals("sheet_id", task.sheetId.get())
        assertEquals("primary_lang", task.primaryLang.get())
        assertEquals(File(project.projectDir, "output"), task.outputDirectory.get().asFile)
        assertEquals(0x000000, task.sectionColour.get())
        assertEquals("section_pattern", task.sectionPattern.get())
        assertEquals(LangFileGenerator, task.configuredFormat.generator)
    }

    @Test
    fun `test incrementing single-digit column header`() {
        val col = Column("A")
        col.increment()
        assertEquals("B", col.header)
    }

    @Test
    fun `test incrementing column header with overflow`() {
        val col = Column("Z")
        col.increment()
        assertEquals("AA", col.header)
    }

    @Test
    fun `test incrementing multi-digit column header`() {
        val col = Column("ABC")
        col.increment()
        assertEquals("ABD", col.header)
    }

    @Test
    fun `test incrementing multi-digit column header containing Z`() {
        val col = Column("ACZ")
        col.increment()
        assertEquals("ADA", col.header)
    }

    @Test
    fun `test incrementing multi-digit column header containing multiple Zs`() {
        val col = Column("DEZZ")
        col.increment()
        assertEquals("DFAA", col.header)
    }

    @Test
    fun `test incrementing multi-digit column header with overflow`() {
        val col = Column("ZZZ")
        col.increment()
        assertEquals("AAAA", col.header)
    }

}
