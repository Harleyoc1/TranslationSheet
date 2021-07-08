package com.harleyoconnor.translationsheet.extension

import org.gradle.api.file.Directory

fun Directory.mkdir(): Directory {
    this.asFile.mkdir()
    return this
}

fun Directory.mkdirs(): Directory {
    this.asFile.mkdirs()
    return this
}