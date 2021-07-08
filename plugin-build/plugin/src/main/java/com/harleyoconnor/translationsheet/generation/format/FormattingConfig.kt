package com.harleyoconnor.translationsheet.generation.format

import java.io.Serializable

/**
 * @author Harley O'Connor
 */
abstract class FormattingConfig(var extension: String? = null) : Serializable {
    companion object {
        private const val serialVersionUID = -125L
    }
}
