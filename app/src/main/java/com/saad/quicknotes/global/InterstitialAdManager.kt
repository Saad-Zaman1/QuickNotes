package com.saad.quicknotes.global

import android.app.Activity
import android.content.Context
import android.util.Log
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

object InterstitialAdManager {

    private var mInterstitialAd: InterstitialAd? = null

    fun loadInterstitialAd(context: Context, adUnitId: String) {
        val adRequest = AdRequest.Builder().build()

        InterstitialAd.load(
            context,
            adUnitId,
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    Log.d("InterstitialAd", "Failed to load ad: $adError")
                    mInterstitialAd = null
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    Log.d("InterstitialAd", "Ad has loaded.")
                    mInterstitialAd = interstitialAd
                }
            }
        )
    }

    fun showInterstitialAd(activity: Activity) {
        if (mInterstitialAd != null) {
            mInterstitialAd?.show(activity)
        } else {
            Log.d("InterstitialAd", "The interstitial ad wasn't ready yet.")
        }
    }

    fun isLoaded(): Boolean {
        return mInterstitialAd != null
    }
}
