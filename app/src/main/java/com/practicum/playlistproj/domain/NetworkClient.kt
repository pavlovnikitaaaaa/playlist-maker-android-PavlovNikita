package com.prcom.practicum.playlistproj.domain

import com.prcom.practicum.playlistproj.data.dto.BaseResponse

interface NetworkClient {
    fun doRequest(dto: Any): BaseResponse
}