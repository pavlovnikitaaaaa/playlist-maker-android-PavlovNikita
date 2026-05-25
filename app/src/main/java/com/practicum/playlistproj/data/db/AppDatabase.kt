package com.prcom.practicum.playlistproj.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.prcom.practicum.playlistproj.data.db.dao.FavoriteTrackDao
import com.prcom.practicum.playlistproj.data.db.dao.PlaylistDao
import com.prcom.practicum.playlistproj.data.db.dao.PlaylistTrackDao
import com.prcom.practicum.playlistproj.data.db.entity.FavoriteTrackEntity
import com.prcom.practicum.playlistproj.data.db.entity.PlaylistEntity
import com.prcom.practicum.playlistproj.data.db.entity.PlaylistTrackEntity

@Database(
    entities = [
        FavoriteTrackEntity::class,
        PlaylistEntity::class,
        PlaylistTrackEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteTrackDao(): FavoriteTrackDao
    abstract fun playlistDao(): PlaylistDao
    abstract fun playlistTrackDao(): PlaylistTrackDao
}
