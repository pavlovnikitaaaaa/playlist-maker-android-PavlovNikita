package com.prcom.practicum.playlistproj

import android.app.Application
import com.prcom.practicum.playlistproj.domain.Creator

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Creator.initialize(this)
    }
}
