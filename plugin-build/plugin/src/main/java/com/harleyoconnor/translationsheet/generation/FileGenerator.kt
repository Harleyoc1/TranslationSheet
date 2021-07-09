package com.harleyoconnor.translationsheet.generation

import com.harleyoconnor.translationsheet.generation.format.FormattingConfig
import java.io.File

/**
 * @author Harley O'Connor
 */
@FunctionalInterface
interface FileGenerator<FC : FormattingConfig> {

    fun generate(config: FC, outputFile: File, translationMap: Map<String, String>)

}
