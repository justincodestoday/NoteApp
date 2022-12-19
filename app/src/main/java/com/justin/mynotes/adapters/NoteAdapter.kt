package com.justin.mynotes.adapters

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.justin.mynotes.R
import com.justin.mynotes.models.Note
import com.justin.mynotes.databinding.NoteItemLayoutBinding

class NoteAdapter(
    private var items: List<Note>,
    val onClick: (note: Note) -> Unit,
    val onLongPress: (note: Note) -> Unit
) :
    RecyclerView.Adapter<NoteAdapter.NoteItemHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteItemHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = NoteItemLayoutBinding.inflate(layoutInflater, parent, false)
        return NoteItemHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteItemHolder, position: Int) {
        val item = items[position]
        holder.binding.run {
            tvTitle.text = item.title
            tvDetails.text = item.details
            cvNoteItem.setCardBackgroundColor(Color.parseColor(item.color))
            cvNoteItem.setOnClickListener {
                onClick(item)
            }
            cvNoteItem.setOnLongClickListener {
                onLongPress(item)
                true
            }
        }
        Log.d("debugging", "Inside onBindViewHolder $position")
    }

    override fun getItemCount() = items.size

    fun setNotes(items: List<Note>) {
        this.items = items
        notifyDataSetChanged()
    }

    class NoteItemHolder(val binding: NoteItemLayoutBinding) : RecyclerView.ViewHolder(binding.root)
}