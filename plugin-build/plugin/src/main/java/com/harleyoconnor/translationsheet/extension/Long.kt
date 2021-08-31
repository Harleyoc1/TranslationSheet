package com.harleyoconnor.translationsheet.extension

fun Long.mapIfLessThan(value: Long, mapper: (Long) -> Long): Long {
    if (this < value) {
        return mapper(this)
    }
    return this
}
