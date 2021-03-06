plugins {
    java
    id("com.harleyoconnor.translationsheet")
}

translationSheet {
    this.sheetId.set("1Zcq9RbptxAmcbg6om-ue1tQY-TD7mOefaDXN8biQW0g")
    this.sectionColour.set(0xF9CB9C)
    this.sectionPattern.set("Dynamic Trees")
    this.outputDir("src/generated/resources/assets/dynamictrees/lang/")

    this.useJson {
        // Demonstrate options to configure Json formatting.
        this.indentation(4)
        this.separator = " : "
    }
}
