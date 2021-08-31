package com.harleyoconnor.translationsheet.extension

import com.google.api.services.sheets.v4.model.Color
import kotlin.math.roundToLong

fun Color.toLong(): Long {
    return  ((((this.red   ?: 1F) * 0xFF).roundToLong() and 0xFF) shl 24) or
            ((((this.green ?: 1F) * 0xFF).roundToLong() and 0xFF) shl 16) or
            ((((this.blue  ?: 1F) * 0xFF).roundToLong() and 0xFF) shl  8) or
            (((this.alpha  ?: 1F) * 0xFF).roundToLong() and 0xFF)
}
