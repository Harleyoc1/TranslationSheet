package com.harleyoconnor.translationsheet.generation

import com.harleyoconnor.translationsheet.extension.forEachLast
import com.harleyoconnor.translationsheet.generation.format.JsonFormattingConfig
import java.io.File

/**
 * @author Harley O'Connor
 */
object JsonFileGenerator : FileGenerator<JsonFormattingConfig> {

    override fun generate(config: JsonFormattingConfig, outputFile: File, translationMap: Map<String, String>) {
        val writer = outputFile.writer()
        writer.write("{\n")

        translationMap.forEachLast { entry, isLast ->
            writer.write(this.getLine(config, entry, isLast))
        }

        writer.write("}")
        writer.close()
    }

    private fun getLine(config: JsonFormattingConfig, entry: Map.Entry<String, String>, isLast: Boolean): String {
        return "${config.indentation}\"${entry.key}\"${config.separator}\"${entry.value}\"" +
            (if (!isLast || config.trailingComma) "," else "") + "\n"
    }

}
