package com.saad.quicknotes.views

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.Firebase
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.analytics
import com.google.firebase.remoteconfig.remoteConfig
import com.google.firebase.remoteconfig.remoteConfigSettings
import com.saad.quicknotes.R
import com.saad.quicknotes.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding

    private lateinit var analytics: FirebaseAnalytics
    private val remoteConfig = Firebase.remoteConfig

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        analytics = Firebase.analytics
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 6
        }
        remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)
        remoteConfig.setConfigSettingsAsync(configSettings)

        fetchAndActivateRemoteConfig()

//        applyChanges()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)


        return binding.root
    }

//    private fun applyChanges() {
//        val updatedValue = remoteConfig.getString("login_text")
//        val updatedColor = remoteConfig.getString("main_color")
//
//        binding.btnlogin.text = updatedValue
//        binding.btnlogin.setBackgroundColor(Color.parseColor(updatedColor))
//
//        //If my location is in Pakistan it will show dark theme, else it will show light theme
//        settheme()
//    }

    private fun fetchAndActivateRemoteConfig() {
        remoteConfig.fetchAndActivate()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val updated = task.result
                    Log.d("Updatedvalue", "Config params updated: $updated")
                } else {
                    Toast.makeText(
                        context, "Fetch failed",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        analytics = FirebaseAnalytics.getInstance(requireContext())
        binding.btnlogin.setOnClickListener {
            val bundle = Bundle()
            val taskname = "TODOLISt"
            bundle.putString("task", "$taskname")
            analytics.logEvent("add_task", bundle)
            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
        }

        binding.materialTextView.setOnClickListener {
//            val bundle = Bundle()
            logCustomEvent("Signup Button", null)
//            bundle.putString("signup", "Signup Button")
//            analytics.logEvent("signup_button", bundle)
            findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
        }
//        remoteConfig.addOnConfigUpdateListener(object : ConfigUpdateListener {
//            override fun onUpdate(configUpdate: ConfigUpdate) {
//                Log.d("UpdatedValue", "Updated keys: " + configUpdate.updatedKeys);
//
//                if (configUpdate.updatedKeys.contains("login_text")) {
//                    remoteConfig.activate().addOnCompleteListener {
//                        Log.d("UpdatedValue", "Login text exists");
//
//                        binding.btnlogin.text = remoteConfig.getString("login_text_with_condition")
//                        binding.btnlogin.setBackgroundColor(
//                            Color.parseColor(
//                                remoteConfig.getString(
//                                    "main_color"
//                                )
//                            )
//                        )
//
//
//                    }
//                }
//            }
//
//            override fun onError(error: FirebaseRemoteConfigException) {
//                Log.w("UpdatedValue", "Config update error with code: " + error.code, error)
//            }
//        })

    }

    private fun logCustomEvent(eventName: String, params: Bundle?) {
        analytics.logEvent(eventName, params)
    }

    private fun settheme() {
        when (remoteConfig.getString("app_theme")) {
            "light" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            "dark" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }

    }

}
