package com.harleyoconnor.translationsheet.extension

fun <K, V> Map<K, V>.forEachLast(action: (Map.Entry<K, V>, Boolean) -> Unit) {
    val iterator = this.iterator()
    for (ignored in 0 until this.entries.size - 1) {
        action.invoke(iterator.next(), false)
    }
    action.invoke(iterator.next(), true)
}
