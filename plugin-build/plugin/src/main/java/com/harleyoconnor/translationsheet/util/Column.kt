package com.harleyoconnor.translationsheet.util

import java.util.regex.Pattern

class Column(var header: String) {

    companion object {
        private val HEADER_PATTERN = Pattern.compile("[A-Z]+")
    }

    init {
        if (!HEADER_PATTERN.matcher(header).matches()) {
            throw IllegalArgumentException("Column header must be a non-empty string of [A-Z] chars.")
        }
    }

    fun increment() {
        val chars = header.toCharArray().toMutableList()
        for (i in chars.size - 1 downTo 0) {
            var currentChar = chars[i]
            if (currentChar == 'Z') {
                if (i == 0) {
                    currentChar = '@'
                    chars.add('A')
                } else {
                    continue
                }
            }
            chars[i] = currentChar + 1
            for (j in i + 1 until chars.size) {
                chars[j] = 'A'
            }
            break
        }
        header = chars.joinToString("")
    }

    override fun toString(): String {
        return header
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Column

        return header == other.header
    }

    override fun hashCode(): Int {
        return header.hashCode()
    }

}