package com.prcom.practicum.playlistproj.data.db

import androidx.room.TypeConverter
import org.json.JSONArray

class Converters {
    @TypeConverter
    fun fromLongList(value: List<Long>): String {
        val array = JSONArray()
        value.forEach { array.put(it) }
        return array.toString()
    }

    @TypeConverter
    fun toLongList(value: String): List<Long> {
        if (value.isBlank()) return emptyList()
        val array = JSONArray(value)
        return (0 until array.length()).map { array.getLong(it) }
    }
}
