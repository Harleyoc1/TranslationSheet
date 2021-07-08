package com.harleyoconnor.translationsheet.generation

import com.harleyoconnor.translationsheet.generation.format.Format
import com.harleyoconnor.translationsheet.generation.format.FormattingConfig
import java.io.File

/**
 * @author Harley O'Connor
 */
interface FileGenerator<FC: FormattingConfig> {

    fun generate(config: FC, outFile: File, translationMap: Map<String, String>)

}
