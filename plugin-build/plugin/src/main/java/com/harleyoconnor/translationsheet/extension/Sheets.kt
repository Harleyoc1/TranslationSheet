package com.harleyoconnor.translationsheet.extension

import com.google.api.services.sheets.v4.Sheets
import java.util.stream.Collectors

fun Sheets.getAsList(sheetId: String, range: String): MutableList<String> {
    return this.spreadsheets().values()
        .get(sheetId, range)
        .execute()
        .getValues()
        .stream()
        .flatMap {
            if (it.isEmpty()) {
                it.add("")
            }
            it.stream()
        }
        .map { it.toString() }
        .collect(Collectors.toList())
}
