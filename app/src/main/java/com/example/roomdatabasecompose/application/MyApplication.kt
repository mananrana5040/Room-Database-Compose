package com.example.roomdatabasecompose.application

import android.app.Application
import com.example.roomdatabasecompose.remoteconfig.RemoteConfigManager

class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        RemoteConfigManager.init()
    }

}