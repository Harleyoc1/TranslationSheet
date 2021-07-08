package com.harleyoconnor.translationsheet.generation

import com.harleyoconnor.translationsheet.extension.forEachLast
import com.harleyoconnor.translationsheet.generation.format.JsonFormattingConfig
import java.io.File

/**
 * @author Harley O'Connor
 */
object JsonFileGenerator: FileGenerator<JsonFormattingConfig> {

    override fun generate(config: JsonFormattingConfig, outFile: File, translationMap: Map<String, String>) {
        val writer = outFile.writer()
        writer.write("{\n")

        translationMap.forEachLast { (key, value), isLast ->
            writer.write("${config.tabSpace}\"$key\"${config.separator}\"$value\"" +
                    (if (!isLast || config.trailingComma) "," else "") + "\n")
        }

        writer.write("}")
        writer.close()
    }

}