package com.example.rickmortycleanmvvm.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.domain.model.Character
import com.example.rickmortycleanmvvm.adapter.diffutilCallback.CharacterDiffUtilCallBack
import com.example.rickmortycleanmvvm.databinding.CharacterElementBinding
import de.hdodenhof.circleimageview.CircleImageView

class CharactersAdapter(private val callback: (character: Character) -> Unit) :
    PagingDataAdapter<Character, CharactersAdapter.ViewHolder>(CharacterDiffUtilCallBack) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = CharacterElementBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(itemBinding)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val character = getItem(position)

        Glide.with(holder.characterCIV.context).load(character?.image).into(holder.characterCIV)
        holder.characterNameTV.text = character?.name
        holder.characterStatusTV.text = character?.status

        holder.characterCL.setOnClickListener {
            character?.let { it1 -> callback(it1) }
        }
    }

    class ViewHolder(itemBinding: CharacterElementBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        val characterCL: ConstraintLayout = itemBinding.characterCL
        val characterCIV: CircleImageView = itemBinding.characterCIV
        val characterNameTV: TextView = itemBinding.characterNameTV
        val characterStatusTV: TextView = itemBinding.characterStatusTV
    }
}
