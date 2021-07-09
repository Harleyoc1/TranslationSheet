package com.harleyoconnor.translationsheet.generation.format

import com.google.common.base.Strings.repeat

/**
 * @author Harley O'Connor
 */
class JsonFormattingConfig : FormattingConfig() {

    companion object {
        private const val serialVersionUID = -67L
    }

    lateinit var tabSpace: String
    var trailingComma: Boolean = false
    var separator: String = ": "

    init {
        tabSpaces(2)
    }

    fun tabSpaces(number: Int) {
        this.tabSpace = repeat(" ", number)
    }

}
