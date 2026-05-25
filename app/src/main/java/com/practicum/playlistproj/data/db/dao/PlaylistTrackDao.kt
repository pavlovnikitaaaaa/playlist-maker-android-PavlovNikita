package com.prcom.practicum.playlistproj.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.prcom.practicum.playlistproj.data.db.entity.PlaylistTrackEntity

@Dao
interface PlaylistTrackDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(track: PlaylistTrackEntity)

    @Query("DELETE FROM playlist_tracks WHERE trackId = :trackId")
    suspend fun deleteById(trackId: Long)

    @Query("SELECT * FROM playlist_tracks WHERE trackId IN (:ids)")
    suspend fun getByIds(ids: List<Long>): List<PlaylistTrackEntity>
}
