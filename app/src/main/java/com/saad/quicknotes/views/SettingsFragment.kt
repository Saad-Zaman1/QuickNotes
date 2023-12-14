package com.saad.quicknotes.views

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.google.ads.mediation.admob.AdMobAdapter
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.firebase.Firebase
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.analytics
import com.saad.quicknotes.R
import com.saad.quicknotes.databinding.FragmentSettingsBinding
import com.saad.quicknotes.global.AllAds


const val SettingTags = "SettingFragmentTags"

class SettingsFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding

    //    private var rewardedAd: RewardedAd? = null
    private lateinit var analytics: FirebaseAnalytics
    private lateinit var adView: AdView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        analytics = Firebase.analytics
//        adView = AdManagerAdView(requireContext())
//        adView.adUnitId = "/6499/example/banner"
//        adView.adUnitId = "ca-app-pub-3940256099942544/2014213617"
//        val adSize = AdSize.BANNER
//
//        adView.setAdSize(adSize)


        //For displaying reward ads
//        utils.rewardedAd?.let { utils.showRewardedAd(requireActivity(), requireContext(), it) }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadBannerAdd()

        AllAds.showNativeAd(
            requireContext(),
            binding.adViewFrame,
            R.layout.native_ad_layout_small,
            SettingTags
        )
    }

    @RequiresApi(Build.VERSION_CODES.R)
    private fun loadBannerAdd() {
        adView = AdView(requireActivity())
        adView.adUnitId = "ca-app-pub-3940256099942544/2014213617"
        val adSize = getAdSize()
        adView.setAdSize(adSize)

        val extras = Bundle()
        extras.putString("collapsible", "bottom")

        val adRequest = AdRequest.Builder()
            .addNetworkExtrasBundle(AdMobAdapter::class.java, extras)
            .build()
        binding.bannerContainer.addView(adView)
        adView.loadAd(adRequest)
    }

    @RequiresApi(Build.VERSION_CODES.R)
    private fun getAdSize(): AdSize {
        val windowMetrics = requireActivity().windowManager.currentWindowMetrics
        val bounds = windowMetrics.bounds

        var adWidthPixels = binding.bannerContainer.width.toFloat()

        // If the ad hasn't been laid out, default to the full screen width.
        if (adWidthPixels == 0f) {
            adWidthPixels = bounds.width().toFloat()
        }

        val density = resources.displayMetrics.density
        val adWidth = (adWidthPixels / density).toInt()

        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(requireContext(), adWidth)
    }
}


//        loadBanner()


// Create an extra parameter that aligns the bottom of the expanded ad to
// the bottom of the bannerView.
//        val extras = Bundle()
//        extras.putString("collapsible", "bottom")
//
//        val adRequest = AdManagerAdRequest.Builder().build()
//        binding.adManagerAdView.loadAd(adRequest)

//        loadNativeAd(binding.)
//        InterstitialAdManager.showInterstitialAd(requireActivity())

//        rewardedAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
//            override fun onAdClicked() {
//                // Called when a click is recorded for an ad.
//                Log.d(TAG, "Ad was clicked.")
//            }
//
//            override fun onAdDismissedFullScreenContent() {
//                // Called when ad is dismissed.
//                // Set the ad reference to null so you don't show the ad a second time.
//                val bundle = Bundle()
//                bundle.putString("RewardAddDismissed", "Add is dismissed")
//                analytics.logEvent("Reward_Add_Dismissed", bundle)
//                Log.d(TAG, "Ad dismissed fullscreen content.")
////                    rewardedAd = null
//            }
//
//
//            override fun onAdImpression() {
//                // Called when an impression is recorded for an ad.
//                Log.d(TAG, "Ad recorded an impression.")
//            }
//
//            override fun onAdShowedFullScreenContent() {
//                val bundle = Bundle()
//                bundle.putString("RewardAddShown", "Add is Shown")
//                analytics.logEvent("Reward_Add_Shown", bundle)
//                Log.d(TAG, "Ad showed fullscreen content.")
//            }
//        }


//        utils.loadNativeAds(
//            requireContext(),
//            binding.frameNativeAd,
//            R.layout.native_ad_layout_small
//        )
//        if (InterstitialAdManager.isLoaded()) {
//            binding.progressBar.visibility = View.GONE
//        }

//        utils.loadSmallMediumSizeNativeAds(
//            requireContext(),
//            R.string.small_medium_native_ads,
//            binding.templateview
//        )


//    private fun loadBanner() {
//
////        adView = AdView(requireContext())
//        adView.adUnitId = "ca-app-pub-3940256099942544/2014213617"
//        val adSize = AdSize.BANNER
//
//        adView.setAdSize(adSize)
//
//        // Create an extra parameter that aligns the bottom of the expanded ad to
//        // the bottom of the bannerView.
//        val extras = Bundle()
//        extras.putString("collapsible", "bottom")
//
////        val adRequest = AdRequest.Builder()
////            .addNetworkExtrasBundle(AdMobAdapter::class.java, extras)
////            .build()
//
////        adView.loadAd(adRequest)
//    }
//    private fun populateNativeAdView(nativeAd: NativeAd, adView: NativeAdView) {
//        // Set the media view.
//        adView.mediaView = adView.findViewById<MediaView>(R.id.ad_media)
//
//        // Set other ad assets.
//        adView.headlineView = adView.findViewById<View>(R.id.ad_headline)
//        adView.bodyView = adView.findViewById<View>(R.id.ad_body)
//        adView.callToActionView = adView.findViewById<View>(R.id.ad_call_to_action)
//        adView.iconView = adView.findViewById<View>(R.id.ad_app_icon)
//        adView.priceView = adView.findViewById<View>(R.id.ad_price)
//        adView.starRatingView = adView.findViewById<View>(R.id.ad_stars)
//        adView.storeView = adView.findViewById<View>(R.id.ad_store)
//        adView.advertiserView = adView.findViewById<View>(R.id.ad_advertiser)
//
//        // The headline and mediaContent are guaranteed to be in every UnifiedNativeAd.
//        (adView.headlineView as TextView?)?.text = nativeAd.headline
//        adView.mediaView!!.mediaContent = nativeAd.mediaContent
//
//        // These assets aren't guaranteed to be in every UnifiedNativeAd, so it's important to
//        // check before trying to display them.
//        if (nativeAd.body == null) {
//            adView.bodyView!!.visibility = View.INVISIBLE
//        } else {
//            adView.bodyView!!.visibility = View.VISIBLE
//            (adView.bodyView as TextView?)?.text = nativeAd.body
//        }
//        if (nativeAd.callToAction == null) {
//            adView.callToActionView!!.visibility = View.INVISIBLE
//        } else {
//            adView.callToActionView!!.visibility = View.VISIBLE
//            (adView.callToActionView as Button?)?.text = nativeAd.callToAction
//        }
//        if (nativeAd.icon == null) {
//            adView.iconView!!.visibility = View.GONE
//        } else {
//            (adView.iconView as ImageView?)!!.setImageDrawable(
//                nativeAd.icon!!.drawable
//            )
//            adView.iconView!!.visibility = View.VISIBLE
//        }
//        if (nativeAd.price == null) {
//            adView.priceView!!.visibility = View.INVISIBLE
//        } else {
//            adView.priceView!!.visibility = View.VISIBLE
//            (adView.priceView as TextView?)?.text = nativeAd.price
//        }
//        if (nativeAd.getStore() == null) {
//            adView.storeView!!.visibility = View.INVISIBLE
//        } else {
//            adView.storeView!!.visibility = View.VISIBLE
//            (adView.storeView as TextView?)?.text = nativeAd.store
//        }
//        if (nativeAd.getStarRating() == null) {
//            adView.starRatingView!!.visibility = View.INVISIBLE
//        } else {
//            (adView.starRatingView as RatingBar?)?.rating = nativeAd.starRating?.toFloat()!!
//            adView.starRatingView!!.visibility = View.VISIBLE
//        }
//        if (nativeAd.advertiser == null) {
//            adView.advertiserView!!.visibility = View.INVISIBLE
//        } else {
//            (adView.advertiserView as TextView?)?.text = nativeAd.advertiser
//            adView.advertiserView!!.visibility = View.VISIBLE
//        }
//
//        // This method tells the Google Mobile Ads SDK that you have finished populating your
//        // native ad view with this native ad.
//        adView.setNativeAd(nativeAd)
//    }


//    private fun loadNativeAd(nativeAdContainer: ViewGroup) {
//        val adLoader = AdLoader.Builder(requireContext(), "ca-app-pub-3940256099942544/2247696110")
//            .forNativeAd { nativeAd ->
//                // Handle the loaded native ad
//                populateNativeAdView(nativeAd, nativeAdContainer)
//            }
//            .build()
//
//        adLoader.loadAd(AdRequest.Builder().build())
//    }
//
//    private fun populateNativeAdView(nativeAd: NativeAd, nativeAdContainer: ViewGroup) {
//        // Inflate the ad layout
//        val adView = LayoutInflater.from(requireContext()).inflate(
//            R.layout.native_ad_layout, nativeAdContainer, false
//        ) as NativeAdView
//
//        // Register the ad view for tracking
//        adView.setNativeAd(nativeAd)
//
//        // Populate the ad components
//        adView.findViewById<TextView>(R.id.ad_headline).text = nativeAd.headline
//        adView.findViewById<TextView>(R.id.ad_body).text = nativeAd.body
//
//        // Load and display the ad image (if available)
//        if (nativeAd.images.isNotEmpty()) {
//            val adImage = adView.findViewById<ImageView>(R.id.ad_image)
//            adImage.setImageDrawable(nativeAd.images[0].drawable)
//        }
//
//        adView.findViewById<Button>(R.id.ad_call_to_action).text = nativeAd.callToAction
//
//        // Add the ad view to your container
//        nativeAdContainer.addView(adView)
//    }

