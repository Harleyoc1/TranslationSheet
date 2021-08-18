package com.harleyoconnor.translationsheet.extension

import com.google.api.services.sheets.v4.model.Color

fun Color.equals(red: Float = 1F, green: Float = 1F, blue: Float = 1F, alpha: Float = 1F): Boolean {
    // Color is stupid.
    return (this.red.toInt() * 256) == (red.toInt() * 256) &&
        (this.green.toInt() * 256) == (green.toInt() * 256) &&
        (this.blue.toInt() * 256) == (blue.toInt() * 256) &&
        ((this.alpha ?: 1).toInt() * 256) == (alpha.toInt() * 256)
}
