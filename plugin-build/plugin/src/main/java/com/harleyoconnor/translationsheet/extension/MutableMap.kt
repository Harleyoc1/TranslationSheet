package com.harleyoconnor.translationsheet.extension

fun <M : MutableMap<K, V>, K, V> M.removeIf(predicate: (K, V) -> Boolean): M {
    for (entry in HashSet(this.entries)) {
        if (predicate(entry.key, entry.value)) {
            this.remove(entry.key)
        }
    }
    return this
}
