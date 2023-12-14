package com.saad.quicknotes.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.firebase.Firebase
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.analytics
import com.saad.quicknotes.BuildConfig
import com.saad.quicknotes.R
import com.saad.quicknotes.adapters.NoteAdapter
import com.saad.quicknotes.databinding.FragmentHomeBinding
import com.saad.quicknotes.global.AllAds
import com.saad.quicknotes.viewmodels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

const val Tag = "HomeFragmentTag"

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var analytics: FirebaseAnalytics


    //    @Inject
//    lateinit var analytics: FirebaseAnalytics


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        binding.lifecycleOwner = viewLifecycleOwner
        val adapter = NoteAdapter()
        binding.recyclerView.adapter = adapter
        analytics = Firebase.analytics

        viewModel.notes.observe(viewLifecycleOwner) { notes ->
            adapter.notes = notes
        }
        AllAds.loadBannerAd(requireActivity(), binding.adViewFrame, BuildConfig.ADMOB_BANNER)
        
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = binding.recyclerView
        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.layoutManager = layoutManager


        binding.btnAddNote.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_addNoteFragment)
        }
    }

}