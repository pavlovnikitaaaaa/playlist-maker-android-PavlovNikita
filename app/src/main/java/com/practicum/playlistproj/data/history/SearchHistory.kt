package com.prcom.practicum.playlistproj.data.history

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.prcom.practicum.playlistproj.data.network.Track
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import org.json.JSONArray
import org.json.JSONObject

private val Context.searchHistoryDataStore by preferencesDataStore(name = "search_history")

class SearchHistory(private val context: Context) {

    private val key = stringPreferencesKey("history_json")

    val history: Flow<List<Track>> = context.searchHistoryDataStore.data.map { prefs ->
        parse(prefs[key] ?: "")
    }

    suspend fun add(track: Track) {
        context.searchHistoryDataStore.edit { prefs ->
            val current = parse(prefs[key] ?: "").toMutableList()
            current.removeAll { it.trackId == track.trackId }
            current.add(0, track)
            while (current.size > MAX_SIZE) {
                current.removeAt(current.lastIndex)
            }
            prefs[key] = serialize(current)
        }
    }

    suspend fun clear() {
        context.searchHistoryDataStore.edit { prefs -> prefs[key] = "" }
    }

    suspend fun snapshot(): List<Track> = parse(
        context.searchHistoryDataStore.data.first()[key] ?: ""
    )

    private fun serialize(tracks: List<Track>): String {
        val array = JSONArray()
        tracks.forEach { track ->
            val obj = JSONObject()
            obj.put("trackId", track.trackId)
            obj.put("trackName", track.trackName)
            obj.put("artistName", track.artistName)
            obj.put("trackTime", track.trackTime)
            obj.put("artworkUrl", track.artworkUrl ?: JSONObject.NULL)
            array.put(obj)
        }
        return array.toString()
    }

    private fun parse(value: String): List<Track> {
        if (value.isBlank()) return emptyList()
        val array = JSONArray(value)
        return (0 until array.length()).map { i ->
            val obj = array.getJSONObject(i)
            Track(
                trackId = obj.getLong("trackId"),
                trackName = obj.getString("trackName"),
                artistName = obj.getString("artistName"),
                trackTime = obj.getString("trackTime"),
                artworkUrl = if (obj.isNull("artworkUrl")) null else obj.getString("artworkUrl")
            )
        }
    }

    companion object {
        private const val MAX_SIZE = 10
    }
}
