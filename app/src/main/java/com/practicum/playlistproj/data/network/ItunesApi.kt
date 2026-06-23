package com.prcom.practicum.playlistproj.data.network

import com.prcom.practicum.playlistproj.data.dto.ItunesSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ItunesApi {
    @GET("search?entity=song")
    suspend fun search(@Query("term") term: String): ItunesSearchResponse
}
