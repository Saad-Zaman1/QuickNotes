package com.saad.quicknotes.global

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdView
import com.saad.quicknotes.views.TAG

object AllAds {
    private var mInterstitialAd: InterstitialAd? = null
    private var mNativeAd: NativeAd? = null
    private lateinit var adView: AdView


    //Interstitial Ads
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

    fun showInterstitialAd(activity: Activity, dismiss: () -> Unit) {
        if (mInterstitialAd != null) {
            mInterstitialAd?.show(activity)
            mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdClicked() {
                    // Called when a click is recorded for an ad.
                    Log.d(TAG, "Ad was clicked.")
                }

                override fun onAdDismissedFullScreenContent() {
                    // Called when ad is dismissed.
                    Log.d(TAG, "Ad dismissed fullscreen content.")
                    mInterstitialAd = null
                    dismiss()
                }

                override fun onAdImpression() {
                    // Called when an impression is recorded for an ad.
                    Log.d(TAG, "Ad recorded an impression.")
                }

                override fun onAdShowedFullScreenContent() {
                    // Called when ad is shown.
                    Log.d(TAG, "Ad showed fullscreen content.")
                }
            }
        } else {
            Log.d("InterstitialAd", "The interstitial ad wasn't ready yet.")
        }
    }

    fun isLoadedInterstitialAd(): Boolean {
        return AllAds.mInterstitialAd != null
    }

    //Banner Ads
//    fun loadBannerAd(adView: AdView) {
//        Log.d("CheckingId", "InsideBannerFunction")
//
//        this.adView = adView
//        val adRequest = AdRequest.Builder().build()
//        this.adView.loadAd(adRequest)
//    }

    fun showBannerAdView(): AdView {
        return this.adView
    }


    fun loadBannerAd(activity: Activity, frameLayout: FrameLayout, adUnitId: String) {
        val adView = AdView(activity)
        adView.adUnitId = adUnitId
        adView.setAdSize(AdSize.BANNER)

        frameLayout.removeAllViews() // Remove any existing views from the container

        frameLayout.addView(adView) // Add the AdView to the FrameLayout

        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)

        adView.adListener = object : AdListener() {
            override fun onAdClicked() {
                // Handle ad click event
            }

            override fun onAdClosed() {
                // Handle ad closed event
            }

            override fun onAdFailedToLoad(adError: LoadAdError) {
                // Handle ad failed to load event
                Log.e("AdError", "Failed to load ad: ${adError.message}")
            }

            override fun onAdImpression() {
                // Handle ad impression event
            }

            override fun onAdLoaded() {
                // Handle ad loaded event
                Log.d("AdLoaded", "Ad loaded successfully")
            }

            override fun onAdOpened() {
                // Handle ad opened event
            }
        }
    }

    //Native Ads
    @SuppressLint("InflateParams")
    fun loadNativeAd(
        context: Context,
        adUnitId: String,
        onAdLoaded: (NativeAd) -> Unit,
        onError: (String) -> Unit
    ) {
        val adLoader = AdLoader.Builder(context, adUnitId)
            .forNativeAd { nativeAd ->
                // Store the loaded NativeAd
                mNativeAd = nativeAd
                onAdLoaded.invoke(nativeAd)
            }
            .withAdListener(object : AdListener() {
                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    super.onAdFailedToLoad(loadAdError)
                    onError.invoke("Ad failed to load: ${loadAdError.message}")
                }
            })
            .build()

        adLoader.loadAd(AdRequest.Builder().build())
    }

    @SuppressLint("InflateParams")
    fun showNativeAd(context: Context, frameLayout: FrameLayout, resource: Int, tag: String) {
        // Check if a NativeAd is loaded
        mNativeAd?.let { nativeAd ->
            val adView = try {
                LayoutInflater.from(context).inflate(
                    resource,
                    frameLayout, // Pass the parent view as the second parameter
                    false
                ) as? NativeAdView
            } catch (e: Exception) {
                Log.e(tag, "Error inflating ad view: ${e.message}")
                null
            }
            Log.d(tag, "$adView")

            adView?.let {
                Log.d(tag, "yoyoHome")
                utils.populateNativeAdViewSmall(nativeAd, it)
                frameLayout.removeAllViews()
                frameLayout.addView(it)
            } ?: Log.e(tag, "AdView is null")
        } ?: Log.e(tag, "No NativeAd loaded")
    }

}