package com.example.rickmortycleanmvvm.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.domain.model.Character
import com.example.rickmortycleanmvvm.adapter.diffutilCallback.CharacterDiffUtilCallBack
import com.example.rickmortycleanmvvm.databinding.CharacterElementFavoriteBinding
import de.hdodenhof.circleimageview.CircleImageView



class CharactersFavoritesAdapter(private val callback: (character: Character) -> Unit) :
    ListAdapter<Character, CharactersFavoritesAdapter.ViewHolder>(CharacterDiffUtilCallBack) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = CharacterElementFavoriteBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(itemBinding)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val character = getItem(position)

        Glide.with(holder.characterFavoriteCIB.context).load(character.image).into(holder.characterFavoriteCIB)
        holder.characterFavoriteNameB.text = character.name

        holder.characterFavoriteNameB.setOnClickListener {
            callback(character)
        }
    }

    class ViewHolder(itemBinding: CharacterElementFavoriteBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        val characterFavoriteCIB: CircleImageView = itemBinding.characterFavoriteCIB
        val characterFavoriteNameB: Button = itemBinding.characterFavoriteNameB
    }
}
