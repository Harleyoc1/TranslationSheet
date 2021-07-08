package com.harleyoconnor.translationsheet.extension

import org.gradle.api.file.Directory
import org.gradle.api.file.RegularFile
import org.gradle.api.provider.Provider
import java.io.File

fun File.createDirs(): File {
    this.mkdirs()
    return this
}

fun <F : RegularFile> Provider<F>.createFile(): Provider<F> {
    this.orNull?.asFile?.createDirs()?.createNewFile()
    return this
}

fun <D : Directory> Provider<D>.createDirs(): Provider<D> {
    this.orNull?.asFile?.mkdirs()
    return this
}
