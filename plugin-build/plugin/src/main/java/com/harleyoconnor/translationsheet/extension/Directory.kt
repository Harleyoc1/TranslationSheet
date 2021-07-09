package com.harleyoconnor.translationsheet.extension

import org.gradle.api.file.Directory
import org.gradle.api.provider.Provider

fun Directory.mkdir(): Directory {
    this.asFile.mkdir()
    return this
}

fun Directory.mkdirs(): Directory {
    this.asFile.mkdirs()
    return this
}

fun <D : Directory> Provider<D>.createDirs(): Provider<D> {
    this.orNull?.asFile?.mkdirs()
    return this
}