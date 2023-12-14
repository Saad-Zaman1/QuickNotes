package com.saad.quicknotes.adapters
// NoteAdapter.kt
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.saad.quicknotes.R
import com.saad.quicknotes.databinding.NoteItemBinding
import com.saad.quicknotes.models.Note


class NoteAdapter :
    RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {
    var notes: List<Note> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: NoteItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.note_item,
            parent,
            false
        )
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(notes[position])
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    inner class NoteViewHolder(private val binding: NoteItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(note: Note) {
//            if (binding.textNoteTitle.text == "") {
//                binding.textNoteTitle.visibility = View.GONE
//            }
            binding.note = note
            binding.executePendingBindings()
        }
    }
}
