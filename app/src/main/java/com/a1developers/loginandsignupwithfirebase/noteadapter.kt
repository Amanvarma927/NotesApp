package com.a1developers.loginandsignupwithfirebase

import android.icu.text.CaseMap.Title
import android.view.LayoutInflater
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.a1developers.loginandsignupwithfirebase.databinding.ActivityAllNotesBinding
import com.a1developers.loginandsignupwithfirebase.databinding.NotesItemBinding




class NoteAdapter(private val notes: List<Noteitem>, private val itemClickListener : OnClickListenerItem) :
    RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

interface OnClickListenerItem{
    fun ondeleteclcik(noteId:String)
    fun onupdateclcik(noteId:String,titleL:String,des:String)
}


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteAdapter.NoteViewHolder {

        val binding = NotesItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)

    }

    override fun onBindViewHolder(holder: NoteAdapter.NoteViewHolder, position: Int) {

        val note = notes[position]
        holder.bind(note)
        holder.binding.Update.setOnClickListener{
            itemClickListener.onupdateclcik(note.noteId,note.title,note.des)
        }
        holder.binding.delete.setOnClickListener{
            itemClickListener.ondeleteclcik(note.noteId)
        }
    }

    override fun getItemCount(): Int {

        return notes.size
    }

    class NoteViewHolder(val binding: NotesItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(note: Noteitem) {

            binding.titleview.text = note.title
            binding.descview.text = note.des

        }

    }
}