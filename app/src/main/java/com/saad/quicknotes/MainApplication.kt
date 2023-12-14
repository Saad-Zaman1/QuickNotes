package com.saad.quicknotes

import android.app.Application
import android.util.Log
import com.google.android.gms.ads.MobileAds
import com.saad.quicknotes.global.AllAds
import com.saad.quicknotes.views.Tag
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        try {
            MobileAds.initialize(applicationContext)
//            AppOpenManger(this)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        //Loading Native Ads
        AllAds.loadNativeAd(
            this,
            BuildConfig.ADMOB_NATIVE,
            onAdLoaded = { nativeAd ->
                Log.d(Tag, nativeAd.toString())
            },
            onError = { error ->
                Log.e(Tag, error)
            })
    }
}
