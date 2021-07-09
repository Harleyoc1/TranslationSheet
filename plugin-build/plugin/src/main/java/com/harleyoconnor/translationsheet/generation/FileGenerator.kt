package com.harleyoconnor.translationsheet.generation

import com.harleyoconnor.translationsheet.generation.format.FormattingConfig
import java.io.File
import java.io.Serializable

/**
 * @author Harley O'Connor
 */
@FunctionalInterface
interface FileGenerator<FC : FormattingConfig> : Serializable {

    companion object {
        private const val serialVersionUID = -79L
    }

    fun generate(config: FC, outputFile: File, translationMap: Map<String, String>)

}
