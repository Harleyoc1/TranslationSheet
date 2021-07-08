package com.harleyoconnor.translationsheet.generation

import com.harleyoconnor.translationsheet.generation.format.EmptyFormattingConfig
import java.io.File

/**
 * @author Harley O'Connor
 */
object LangFileGenerator: FileGenerator<EmptyFormattingConfig> {

    override fun generate(config: EmptyFormattingConfig, outFile: File, translationMap: Map<String, String>) {
        val writer = outFile.writer()

        translationMap.forEach { (key, value) ->
            writer.write("$key=$value")
        }

        writer.close()
    }

}
