package com.harleyoconnor.translationsheet.generation.format

import java.io.Serializable

/**
 * @author Harley O'Connor
 */
class ConfiguredFormat<F: Format, FC: FormattingConfig> (val format: F, val config: FC) : Serializable