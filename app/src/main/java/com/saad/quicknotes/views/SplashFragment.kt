package com.saad.quicknotes.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.saad.quicknotes.BuildConfig
import com.saad.quicknotes.R
import com.saad.quicknotes.databinding.FragmentSplashBinding
import com.saad.quicknotes.global.AllAds
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashFragment : Fragment() {
    private lateinit var binding: FragmentSplashBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        CoroutineScope(Dispatchers.Main).launch {
            //ads load
            AllAds.loadInterstitialAd(requireContext(), BuildConfig.ADMOB_INTERSTITIAL)

            delay(4000)
            AllAds.showInterstitialAd(requireActivity()) {
                findNavController().navigate(R.id.navigate_splashFragment_to_loginFragment2)
            }
//            val navigation: Unit =
//                findNavController().navigate(R.id.navigate_splashFragment_to_loginFragment2)
        }
    }

}