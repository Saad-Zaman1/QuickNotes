package com.saad.quicknotes.database.firebase

import android.util.Log
import com.google.firebase.remoteconfig.ConfigUpdate
import com.google.firebase.remoteconfig.ConfigUpdateListener
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigException
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import javax.inject.Inject

class RemoteConfigManager @Inject constructor(
    private val remoteConfig: FirebaseRemoteConfig,
    private val configSettings: FirebaseRemoteConfigSettings
) {
    fun fetchRemoteConfig() {
        // Implement the logic to fetch Remote Config values here
        remoteConfig.fetchAndActivate()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val updated = task.result
                    // Handle Remote Config fetch success
                } else {
                    // Handle Remote Config fetch failure
                }
            }
    }

    fun realtimeRemoteConfig() {
        remoteConfig.addOnConfigUpdateListener(object : ConfigUpdateListener {
            override fun onUpdate(configUpdate: ConfigUpdate) {
                Log.d("UpdatedValue", "Updated keys: " + configUpdate.updatedKeys);

                if (configUpdate.updatedKeys.contains("login_text")) {
                    remoteConfig.activate().addOnCompleteListener {
                        Log.d("UpdatedValue", "Login text exists");
                    }
                }
            }

            override fun onError(error: FirebaseRemoteConfigException) {
                Log.w("UpdatedValue", "Config update error with code: " + error.code, error)
            }
        })
    }

    fun getString(key: String): String {
        return remoteConfig.getString(key)
    }
}