package com.harleyoconnor.translationsheet.generation.format

import com.harleyoconnor.translationsheet.generation.FileGenerator
import com.harleyoconnor.translationsheet.generation.JsonFileGenerator
import com.harleyoconnor.translationsheet.generation.LangFileGenerator
import java.io.Serializable

interface Format: Serializable {
    fun getIdentifier(): String
    fun getExtensions(): Array<String>
    fun getDefaultExtension(): String
}

abstract class AbstractFormat(private val identifier: String, private val extensions: Array<String>): Format {
    override fun getIdentifier(): String = this.identifier
    override fun getExtensions(): Array<String> = this.extensions
    override fun getDefaultExtension(): String = this.extensions[0]
}

object Json: AbstractFormat("Json", arrayOf("json"))

object Lang: AbstractFormat("Lang", arrayOf("lang"))

val FORMAT_GENERATORS: Map<Format, FileGenerator<*>> = mapOf(
    Json to JsonFileGenerator,
    Lang to LangFileGenerator
)

@Suppress("UNCHECKED_CAST")
fun <F: Format, FC: FormattingConfig> getGenerator(configuredFormat: ConfiguredFormat<F, FC>): FileGenerator<FC> {
    return FORMAT_GENERATORS[configuredFormat.format] as FileGenerator<FC>
}