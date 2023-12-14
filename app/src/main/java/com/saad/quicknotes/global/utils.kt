package com.saad.quicknotes.global

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.OnUserEarnedRewardListener
import com.google.android.gms.ads.nativead.MediaView
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdView
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.saad.quicknotes.R
import com.saad.quicknotes.views.TAG


object utils {
    var rewardedAd: RewardedAd? = null


//    fun loadSmallMediumSizeNativeAds(context: Context, adUnitIdL: Int, templateView: TemplateView) {
//        val adLoader = AdLoader.Builder(context, context.getString(adUnitIdL))
//            .forNativeAd { nativeAd ->
//                templateView.visibility = View.VISIBLE
//                templateView.setNativeAd(nativeAd)
//            }.withAdListener(object : AdListener() {
//                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
//                    templateView.visibility = View.GONE
//                }
//            }).build()
//        adLoader.loadAd(AdRequest.Builder().build())
//    }


    @SuppressLint("InflateParams")
    fun loadNativeAds(context: Context, frameLayout: FrameLayout, resource: Int) {
        val adLoader = AdLoader.Builder(context, "ca-app-pub-3940256099942544/2247696110")
            .forNativeAd { nativeAd ->
                // Handle the loaded native ad
                val adView = try {
                    LayoutInflater.from(context).inflate(
                        resource,
                        frameLayout, // Pass the parent view as the second parameter
                        false
                    ) as? NativeAdView
                } catch (e: Exception) {
                    Log.e("InsideLet", "Error inflating ad view: ${e.message}")
                    null
                }
                Log.d("InsideLet", "$adView")

                adView?.let {
                    Log.d("InsideLet", "yoyoHome")
                    utils.populateNativeAdViewSmall(nativeAd, it)
                    frameLayout.removeAllViews()
                    frameLayout.addView(it)
                } ?: Log.e("InsideLet", "AdView is null")
            }
            .build()

        adLoader.loadAd(AdRequest.Builder().build())
    }

    fun loadRewardAds(context: Context, callback: (RewardedAd?) -> Unit) {
        val adRequest = AdRequest.Builder().build()
        RewardedAd.load(
            context,
            "ca-app-pub-3940256099942544/5224354917",
            adRequest,
            object : RewardedAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    adError.toString().let { Log.d(TAG, it) }
                    callback(null)
                }

                override fun onAdLoaded(ad: RewardedAd) {
                    Log.d(TAG, "Ad was loaded.")
                    callback(ad)
                }
            }
        )
    }

    // Function to show the loaded rewarded ad
    fun showRewardedAd(activity: Activity, context: Context, rewardedAd: RewardedAd) {
//        if (rewardedAd.isLoaded) {
//            val adCallback = object : RewardedAdCallback() {
//                override fun onUserEarnedReward(rewardItem: RewardItem) {
//                    // Handle the reward earned by the user
//                    Log.d(TAG, "User earned a reward.")
//                }
//
//                override fun onRewardedAdClosed() {
//                    // Ad closed, you may want to load a new rewarded ad here
//                    Log.d(TAG, "Rewarded ad closed.")
//                }
//            }
//
//            rewardedAd.show(context, adCallback)
//        } else {
//            Log.d(TAG, "Rewarded ad is not loaded.")
//        }

        rewardedAd.let { ad ->
            ad.show(activity, OnUserEarnedRewardListener { rewardItem ->
                // Handle the reward.
                val rewardAmount = rewardItem.amount

                val rewardType = rewardItem.type
                Log.d("Rewardis", "User earned the reward. $rewardAmount")

            })
        }
        rewardedAd.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdClicked() {
                // Called when a click is recorded for an ad.
                Log.d(TAG, "Ad was clicked.")
            }

            override fun onAdDismissedFullScreenContent() {
                // Called when ad is dismissed.
                // Set the ad reference to null so you don't show the ad a second time.
                Log.d(TAG, "Ad dismissed fullscreen content.")
            }

            override fun onAdShowedFullScreenContent() {
                // Called when ad is shown.
                Log.d(TAG, "Ad showed fullscreen content.")
            }
        }
    }


    fun populateNativeAdViewSmall(nativeAd: NativeAd, adView: NativeAdView) {
        // Set the media view.
        adView.iconView = adView.findViewById<View>(R.id.ad_app_icon)

        // Set other ad assets.
        adView.headlineView = adView.findViewById<View>(R.id.ad_headline)
        adView.bodyView = adView.findViewById<View>(R.id.ad_body)
        adView.callToActionView = adView.findViewById<View>(R.id.ad_call_to_action)

        if (nativeAd.headline == null) {
            adView.headlineView!!.visibility = View.INVISIBLE
        } else {
            adView.headlineView!!.visibility = View.VISIBLE
            (adView.headlineView as TextView?)?.text = nativeAd.headline
        }
        if (nativeAd.body == null) {
            adView.bodyView!!.visibility = View.INVISIBLE
        } else {
            adView.bodyView!!.visibility = View.VISIBLE
            (adView.bodyView as TextView?)?.text = nativeAd.body
        }
        if (nativeAd.callToAction == null) {
            adView.callToActionView!!.visibility = View.INVISIBLE
        } else {
            adView.callToActionView!!.visibility = View.VISIBLE
            (adView.callToActionView as Button?)?.text = nativeAd.callToAction
        }
        if (nativeAd.icon == null) {
            adView.iconView!!.visibility = View.GONE
        } else {
            (adView.iconView as ImageView?)!!.setImageDrawable(
                nativeAd.icon!!.drawable
            )
            adView.iconView!!.visibility = View.VISIBLE
        }
        adView.setNativeAd(nativeAd)
    }

    fun populateNativeAdView(nativeAd: NativeAd, adView: NativeAdView) {
        // Set the media view.
        adView.mediaView = adView.findViewById<MediaView>(R.id.ad_media)

        // Set other ad assets.
        adView.headlineView = adView.findViewById<View>(R.id.ad_headline)
        adView.bodyView = adView.findViewById<View>(R.id.ad_body)
        adView.callToActionView = adView.findViewById<View>(R.id.ad_call_to_action)
        adView.iconView = adView.findViewById<View>(R.id.ad_app_icon)
        adView.priceView = adView.findViewById<View>(R.id.ad_price)
        adView.starRatingView = adView.findViewById<View>(R.id.ad_stars)
        adView.storeView = adView.findViewById<View>(R.id.ad_store)
        adView.advertiserView = adView.findViewById<View>(R.id.ad_advertiser)

        // The headline and mediaContent are guaranteed to be in every UnifiedNativeAd.
        (adView.headlineView as TextView?)?.text = nativeAd.headline
        adView.mediaView!!.mediaContent = nativeAd.mediaContent

        // These assets aren't guaranteed to be in every UnifiedNativeAd, so it's important to
        // check before trying to display them.
        if (nativeAd.body == null) {
            adView.bodyView!!.visibility = View.INVISIBLE
        } else {
            adView.bodyView!!.visibility = View.VISIBLE
            (adView.bodyView as TextView?)?.text = nativeAd.body
        }
        if (nativeAd.callToAction == null) {
            adView.callToActionView!!.visibility = View.INVISIBLE
        } else {
            adView.callToActionView!!.visibility = View.VISIBLE
            (adView.callToActionView as Button?)?.text = nativeAd.callToAction
        }
        if (nativeAd.icon == null) {
            adView.iconView!!.visibility = View.GONE
        } else {
            (adView.iconView as ImageView?)!!.setImageDrawable(
                nativeAd.icon!!.drawable
            )
            adView.iconView!!.visibility = View.VISIBLE
        }
        if (nativeAd.price == null) {
            adView.priceView!!.visibility = View.INVISIBLE
        } else {
            adView.priceView!!.visibility = View.VISIBLE
            (adView.priceView as TextView?)?.text = nativeAd.price
        }
        if (nativeAd.getStore() == null) {
            adView.storeView!!.visibility = View.INVISIBLE
        } else {
            adView.storeView!!.visibility = View.VISIBLE
            (adView.storeView as TextView?)?.text = nativeAd.store
        }
        if (nativeAd.getStarRating() == null) {
            adView.starRatingView!!.visibility = View.INVISIBLE
        } else {
            (adView.starRatingView as RatingBar?)?.rating = nativeAd.starRating?.toFloat()!!
            adView.starRatingView!!.visibility = View.VISIBLE
        }
        if (nativeAd.advertiser == null) {
            adView.advertiserView!!.visibility = View.INVISIBLE
        } else {
            (adView.advertiserView as TextView?)?.text = nativeAd.advertiser
            adView.advertiserView!!.visibility = View.VISIBLE
        }

        // This method tells the Google Mobile Ads SDK that you have finished populating your
        // native ad view with this native ad.
        adView.setNativeAd(nativeAd)
    }

}