package com.saad.quicknotes.global

import android.app.Activity
import android.app.Application.ActivityLifecycleCallbacks
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.appopen.AppOpenAd
import com.saad.quicknotes.MainApplication
import com.saad.quicknotes.R
import java.util.Date

class AppOpenManger(private val myApp: MainApplication) : ActivityLifecycleCallbacks,
    DefaultLifecycleObserver {

    private var appOpenAd: AppOpenAd? = null
    private var currentActivity: Activity? = null
    private var isShowingAd = false
    private var loadTime: Long = 0


    init {
        myApp.registerActivityLifecycleCallbacks(this)
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }


    private fun wasLoadTimeLessThanNHoursAgo(): Boolean {
        val numHours = 4
        val dateDifference: Long = Date().time - loadTime
        val numMilliSecondsPerHour: Long = 3600000
        return dateDifference < numMilliSecondsPerHour * numHours
    }

    private fun isAdAvailable(): Boolean {
        return appOpenAd != null && wasLoadTimeLessThanNHoursAgo()
    }

    fun fetchAd() {
        if (isAdAvailable()) {
            return
        }
        val loadCallbacks = object : AppOpenAd.AppOpenAdLoadCallback() {
            override fun onAdLoaded(appOpenAd: AppOpenAd) {
                super.onAdLoaded(appOpenAd)
                this@AppOpenManger.appOpenAd = appOpenAd
                loadTime = Date().time
            }
        }
        val request = AdRequest.Builder().build()
        AppOpenAd.load(
            myApp,
            currentActivity!!.getString(R.string.appOpenAds),
            request,
            loadCallbacks
        )
    }

    private fun showAdIfAvailable() {
        if (!isShowingAd && isAdAvailable()) {
            appOpenAd!!.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    appOpenAd = null
                    isShowingAd = false
                    fetchAd()
                }

                override fun onAdShowedFullScreenContent() {
                    isShowingAd = true
                }
            }
            appOpenAd!!.show(currentActivity!!)
            appOpenAd?.fullScreenContentCallback = object : FullScreenContentCallback() {

                override fun onAdDismissedFullScreenContent() {
                    // Called when full screen content is dismissed.
                    // Set the reference to null so isAdAvailable() returns false.

                    Log.d("AddOpenApp", "Ad dismissed fullscreen content.")
                    appOpenAd = null
                    isShowingAd = false

                }

                override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                    // Called when fullscreen content failed to show.
                    // Set the reference to null so isAdAvailable() returns false.
                    Log.d("AddOpenApp", adError.message)
                    appOpenAd = null
                    isShowingAd = false

                }

                override fun onAdShowedFullScreenContent() {
                    // Called when fullscreen content is shown.
                    Log.d("AssOpenApp", "Ad showed fullscreen content.")
                }
            }
        } else {
            fetchAd()
        }
    }

    override fun onActivityCreated(activity: Activity, p1: Bundle?) {
        if (!isShowingAd) {
            currentActivity = activity
        }
    }

    override fun onActivityStarted(activity: Activity) {

    }

    override fun onActivityResumed(activity: Activity) {
//        if (!isShowingAd) {
//            currentActivity = activity
//        }
    }

    override fun onActivityPaused(p0: Activity) {

    }

    override fun onActivityStopped(p0: Activity) {

    }

    override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {

    }

    override fun onActivityDestroyed(p0: Activity) {
        currentActivity = null
    }

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        showAdIfAvailable()
    }


}