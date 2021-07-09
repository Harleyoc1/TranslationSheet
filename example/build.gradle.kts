plugins {
    java
    id("com.harleyoconnor.translationsheet")
}

val modId: String = "modid"

translationFilesGeneration {
    this.sheetId.set("")
    this.outputDir("src/generated/resources/assets/$modId/lang/")

    // Demonstrate options to configure Json formatting.
    this.useJson({
        this.tabSpaces(4)
        this.separator = " : "
    }, { config, outputFile, translationMap ->

    })
}
