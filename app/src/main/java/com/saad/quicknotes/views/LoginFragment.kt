package com.saad.quicknotes.views

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.firebase.Firebase
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.analytics
import com.saad.quicknotes.R
import com.saad.quicknotes.database.firebase.RemoteConfigManager
import com.saad.quicknotes.databinding.FragmentLoginBinding
import com.saad.quicknotes.global.InterstitialAdManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private var mInterstitialAd: InterstitialAd? = null

    @Inject
    lateinit var remoteConfigManager: RemoteConfigManager


    private lateinit var analytics: FirebaseAnalytics
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        analytics = Firebase.analytics
//        InterstitialAdManager.loadInterstitialAd(
//            requireContext(),
//            "ca-app-pub-3940256099942544/1033173712"
//        )
//        AllAds.showInterstitialAd(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        applyChanges()

        binding.btnGoogle.setOnClickListener {
            InterstitialAdManager.loadInterstitialAd(
                requireContext(),
                "ca-app-pub-3940256099942544/1033173712"
            )
            findNavController().navigate(R.id.action_loginFragment_to_settingsFragment)
        }
        binding.btnlogin.setOnClickListener {
            val bundle = Bundle()
            val taskname = "TODOLISt"
            bundle.putString("task", taskname)
            analytics.logEvent("add_task", bundle)
            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
        }

        binding.materialTextView.setOnClickListener {
//            val bundle = Bundle()
            logCustomEvent("Signup Button", null)
//            bundle.putString("signup", "Signup Button")
//            analytics.logEvent("signup_button", bundle)
            val adRequest = AdRequest.Builder().build()

            InterstitialAd.load(
                requireContext(),
                "ca-app-pub-3940256099942544/1033173712",
                adRequest,
                object : InterstitialAdLoadCallback() {
                    override fun onAdFailedToLoad(adError: LoadAdError) {
                        adError.toString().let { Log.d(TAG, it) }
                        mInterstitialAd = null
                    }

                    override fun onAdLoaded(interstitialAd: InterstitialAd) {
                        Log.d(TAG, "ad has loaded.")
                        mInterstitialAd = interstitialAd
                    }
                })

            findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
        }


    }

    private fun logCustomEvent(eventName: String, params: Bundle?) {
        analytics.logEvent(eventName, params)
    }

    private fun applyChanges() {

        val updatedValue = remoteConfigManager.getString("login_text")
        val updatedColor = remoteConfigManager.getString("main_color").trim()
        Log.d("Updatedvalue", "Config params updated: $updatedValue")
        Log.d("Updatedvalue", "Config params updated: $updatedColor")

        binding.btnlogin.text = updatedValue
        binding.btnlogin.setBackgroundColor(Color.parseColor(updatedColor))

        //If my location is in Pakistan it will show dark theme, else it will show light theme
        settheme()
    }

    private fun settheme() {
        when (remoteConfigManager.getString("app_theme")) {
            "light" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            "dark" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }

    }


}
