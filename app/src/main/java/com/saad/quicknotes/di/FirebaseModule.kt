package com.saad.quicknotes.di

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.saad.quicknotes.database.firebase.RemoteConfigManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

//    @Provides
//    @Singleton
//    fun provideFirebaseAnalytics(application: Application): FirebaseAnalytics {
//        return FirebaseAnalytics.getInstance(application)
//    }

    @Provides
    @Singleton
    fun provideFirebaseRemoteConfig(): FirebaseRemoteConfig {
        return FirebaseRemoteConfig.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirebaseRemoteConfigSettings(): FirebaseRemoteConfigSettings {
        return FirebaseRemoteConfigSettings.Builder()
            .setMinimumFetchIntervalInSeconds(6)
            .build()
    }

    @Provides
    @Singleton
    fun provideRemoteConfigManager(
        remoteConfig: FirebaseRemoteConfig,
        configSettings: FirebaseRemoteConfigSettings
    ): RemoteConfigManager {
        val remoteConfigManager = RemoteConfigManager(remoteConfig, configSettings)
        remoteConfigManager.fetchRemoteConfig()
        return RemoteConfigManager(remoteConfig, configSettings)
    }
}