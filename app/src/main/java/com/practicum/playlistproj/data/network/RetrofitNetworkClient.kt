package com.prcom.practicum.playlistproj.data.network

import com.prcom.practicum.playlistproj.data.dto.BaseResponse
import com.prcom.practicum.playlistproj.data.dto.TracksSearchRequest
import com.prcom.practicum.playlistproj.data.dto.TracksSearchResponse
import com.prcom.practicum.playlistproj.domain.NetworkClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitNetworkClient : NetworkClient {

    private val api: ItunesApi = Retrofit.Builder()
        .baseUrl("https://itunes.apple.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ItunesApi::class.java)

    override suspend fun doRequest(dto: Any): BaseResponse {
        if (dto !is TracksSearchRequest) {
            return BaseResponse().apply { resultCode = 400 }
        }
        return try {
            val response = api.search(dto.expression)
            TracksSearchResponse(response.results).apply { resultCode = 200 }
        } catch (e: Throwable) {
            BaseResponse().apply { resultCode = -1 }
        }
    }
}
