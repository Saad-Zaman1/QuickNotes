package com.saad.quicknotes.views

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.google.ads.mediation.admob.AdMobAdapter
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError
import com.saad.quicknotes.databinding.FragmentSignUpBinding
import com.saad.quicknotes.global.InterstitialAdManager
import com.saad.quicknotes.global.utils


class SignUpFragment : Fragment() {

    //    private var mInterstitialAd: InterstitialAd? = null
    private lateinit var binding: FragmentSignUpBinding
    private lateinit var adView: AdView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        utils.loadRewardAds(requireContext()) {
            utils.rewardedAd = it
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadBannerAdd()
        binding.materialTextView.setOnClickListener {
            Log.d(TAG, "Clicked")
            InterstitialAdManager.showInterstitialAd(requireActivity())
        }

        binding.btnGoogle.setOnClickListener {
            //For displaying reward ads
            utils.rewardedAd?.let { utils.showRewardedAd(requireActivity(), requireContext(), it) }
        }

    }

    @RequiresApi(Build.VERSION_CODES.R)
    private fun loadBannerAdd() {
        adView = AdView(requireActivity())
        adView.adUnitId = "ca-app-pub-3940256099942544/2014213617"
        val adSize = getAdSize()
        adView.setAdSize(adSize)
        adView.adListener = object : AdListener() {
            override fun onAdSwipeGestureClicked() {
                super.onAdSwipeGestureClicked()
                Log.d("BannerAd", "Add Swiped")
            }

            override fun onAdClicked() {
                Log.d("BannerAd", "onAdClicked")
            }

            override fun onAdClosed() {
                Log.d("BannerAd", "onAdClosed")

            }

            override fun onAdFailedToLoad(adError: LoadAdError) {
                Log.d("BannerAd", "onAdFailedToLoad")

                // Code to be executed when an ad request fails.
            }

            override fun onAdImpression() {
                Log.d("BannerAd", "onAdImpression")

                // Code to be executed when an impression is recorded
                // for an ad.
            }

            override fun onAdLoaded() {
                Log.d("BannerAd", "onAdLoaded")

                // Code to be executed when an ad finishes loading.
            }

            override fun onAdOpened() {
                Log.d("BannerAd", "onAdOpened")

                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }
        }

        val extras = Bundle()
        extras.putString("collapsible", "top")

        val adRequest = AdRequest.Builder()
            .addNetworkExtrasBundle(AdMobAdapter::class.java, extras)
            .build()
        binding.bannerContainerSignUp.addView(adView)
        adView.loadAd(adRequest)
    }

    @RequiresApi(Build.VERSION_CODES.R)
    private fun getAdSize(): AdSize {
        val windowMetrics = requireActivity().windowManager.currentWindowMetrics
        val bounds = windowMetrics.bounds

        var adWidthPixels = binding.bannerContainerSignUp.width.toFloat()

        // If the ad hasn't been laid out, default to the full screen width.
        if (adWidthPixels == 0f) {
            adWidthPixels = bounds.width().toFloat()
        }

        val density = resources.displayMetrics.density
        val adWidth = (adWidthPixels / density).toInt()

        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(requireContext(), adWidth)
    }
}


