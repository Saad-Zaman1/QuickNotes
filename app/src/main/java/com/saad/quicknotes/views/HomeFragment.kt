package com.saad.quicknotes.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.saad.quicknotes.adapters.NoteAdapter
import com.saad.quicknotes.databinding.FragmentHomeBinding
import com.saad.quicknotes.models.Note


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = binding.recyclerView
        val layoutManager = LinearLayoutManager(requireContext())
        recyclerView.layoutManager = layoutManager


        val noteList: List<Note> = listOf(
            Note("Note 1", "This is the content testing of Note 1."),
            Note("Note 2", "Content for Note 2."),
            Note("Note 3", "Content of the third note."),
            // Add more dummy notes as needed
        )
        val adapter = NoteAdapter(noteList)
        recyclerView.adapter = adapter
    }
}