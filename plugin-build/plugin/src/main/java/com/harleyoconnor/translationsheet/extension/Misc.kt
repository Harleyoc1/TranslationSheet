package com.harleyoconnor.translationsheet.extension

import org.gradle.api.file.Directory
import org.gradle.api.file.RegularFile
import org.gradle.api.provider.Provider

fun <F : RegularFile> Provider<F>.createFile(): Provider<F> {
    this.orNull?.asFile?.createNewFile()
    return this
}

fun <D : Directory> Provider<D>.createDirs(): Provider<D> {
    this.orNull?.asFile?.mkdirs()
    return this
}
