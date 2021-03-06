package com.erikriosetiawan.jolalinotes.adapters

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.erikriosetiawan.jolalinotes.R
import com.erikriosetiawan.jolalinotes.databinding.ItemNoteBinding
import com.erikriosetiawan.jolalinotes.models.Note
import com.erikriosetiawan.jolalinotes.utils.dateFormat

class NoteAdapter(private val context: Context, private val notes: List<Note>) :
    RecyclerView.Adapter<NoteAdapter.ViewHolder>() {

    private var onItemClickListener: ((Note) -> Unit)? = null

    fun setOnItemClickListener(listener: (Note) -> Unit) {
        onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val binding = ItemNoteBinding.inflate(layoutInflater)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteAdapter.ViewHolder, position: Int) {
        holder.bind(notes[position], position, onItemClickListener)
    }

    override fun getItemCount(): Int = notes.size

    inner class ViewHolder(private val binding: ItemNoteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(note: Note, position: Int, clickListener: ((Note) -> Unit)?) {
            binding.tvTitle.text = note.title
            binding.tvDescription.text = note.description
            binding.tvDate.text = context.dateFormat(note.date)
            binding.tvFirstCharacter.text = note.title[0].toString()
            binding.imgIcon.apply {
                when {
                    (position + 1) % 2 == 0 -> setImageDrawable(
                        ColorDrawable(
                            ContextCompat.getColor(
                                context,
                                R.color.colorIconTwo
                            )
                        )
                    )
                    else -> setImageDrawable(
                        ColorDrawable(
                            ContextCompat.getColor(
                                context,
                                R.color.colorIconOne
                            )
                        )
                    )
                }
            }

            itemView.setOnClickListener { clickListener?.let { it(note) } }
        }
    }
}