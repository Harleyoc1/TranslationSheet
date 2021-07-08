import com.harleyoconnor.translationsheet.extension.mkdirs

plugins {
    java
    id("com.harleyoconnor.translationsheet")
}

val modId: String = "modid"

translationFilesGeneration {
    this.sheetId.set("")
    this.outputDirectory.set(project.layout.projectDirectory.dir("src/generated/resources/assets/$modId/lang/").mkdirs())

    // Demonstrate options to configure Json formatting.
    this.useJson {
        this.tabSpaces(4)
        this.separator = " : "
    }
}
