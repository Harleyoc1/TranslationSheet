package com.harleyoconnor.translationsheet.extension

fun <C : MutableCollection<E>, E> C.fillTo(size: Int, element: E): C {
    for (i in this.size until size) {
        this.add(element)
    }
    return this
}
