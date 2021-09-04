package com.harleyoconnor.translationsheet.generation.format

import com.google.common.base.Strings.repeat

/**
 * @author Harley O'Connor
 */
class JsonFormattingConfig : FormattingConfig() {

    companion object {
        private const val serialVersionUID = -67L
    }

    lateinit var indentation: String
    var trailingComma: Boolean = false
    var separator: String = ": "

    init {
        this.indentation(2)
    }

    fun indentation(number: Int) {
        this.indentation = repeat(" ", number)
    }

}
