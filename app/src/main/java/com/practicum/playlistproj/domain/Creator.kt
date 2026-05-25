package com.prcom.practicum.playlistproj.domain

import android.content.Context
import androidx.room.Room
import com.prcom.practicum.playlistproj.data.db.AppDatabase
import com.prcom.practicum.playlistproj.data.db.FavoritesRepositoryImpl
import com.prcom.practicum.playlistproj.data.db.PlaylistsRepositoryImpl
import com.prcom.practicum.playlistproj.data.history.SearchHistory
import com.prcom.practicum.playlistproj.data.network.RetrofitNetworkClient
import com.prcom.practicum.playlistproj.data.network.TracksRepositoryImpl

object Creator {
    private lateinit var appContext: Context
    private lateinit var database: AppDatabase

    fun initialize(context: Context) {
        appContext = context.applicationContext
        database = Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "playlist_maker.db"
        ).build()
    }

    fun getTracksRepository(): TracksRepository {
        return TracksRepositoryImpl(
            networkClient = RetrofitNetworkClient(),
            searchHistory = SearchHistory(appContext)
        )
    }

    fun getFavoritesRepository(): FavoritesRepository {
        return FavoritesRepositoryImpl(database.favoriteTrackDao())
    }

    fun getPlaylistsRepository(): PlaylistsRepository {
        return PlaylistsRepositoryImpl(
            database.playlistDao(),
            database.playlistTrackDao()
        )
    }
}
