package com.example.roomdatabasecompose.remoteconfig

import android.util.Log
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.remoteConfig
import com.google.firebase.remoteconfig.remoteConfigSettings

object RemoteConfigManager {

    private val remoteConfig: FirebaseRemoteConfig by lazy { Firebase.remoteConfig }

    fun init() {
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 0
        }
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.setDefaultsAsync(mapOf("show_extra_button" to false))
        fetchRemoteConfig()
    }

    private fun fetchRemoteConfig() {
        remoteConfig.fetchAndActivate().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("Firebase", "Config fetch and activate")
            }else{
                Log.d("Firebase", "Config fetch failed ")
            }
        }
    }

    fun shouldShowExtraButton(): Boolean = remoteConfig.getBoolean("show_extra_button")

    fun changeBtnText(): String = remoteConfig.getString("change_text")

}