package com.harleyoconnor.translationsheet.generation.format

import com.harleyoconnor.translationsheet.generation.FileGenerator
import java.io.File
import java.io.Serializable

/**
 * @author Harley O'Connor
 */
class ConfiguredFormat<F : Format, FC : FormattingConfig> (val format: F, val config: FC, val generator: FileGenerator<FC>) : Serializable {
    companion object {
        private const val serialVersionUID = -99L

        fun <F : Format, FC : FormattingConfig> create(
            format: F,
            config: FC,
            generator: (FC, File, Map<String, String>) -> Unit
        ):
            ConfiguredFormat<F, FC> {
                return ConfiguredFormat(
                    format,
                    config,
                    object : FileGenerator<FC> {
                        override fun generate(config: FC, outputFile: File, translationMap: Map<String, String>) {
                            generator.invoke(config, outputFile, translationMap)
                        }
                    }
                )
            }
    }
}
