package com.example.rickmortycleanmvvm.adapter.diffutilCallback

import androidx.recyclerview.widget.DiffUtil
import com.example.domain.model.Character

object CharacterDiffUtilCallBack : DiffUtil.ItemCallback<Character>() {
    override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean =
            newItem.gender == oldItem.gender &&
            newItem.id == oldItem.id &&
            newItem.image == oldItem.image &&
            newItem.name == oldItem.name &&
            newItem.status == oldItem.status &&
                newItem.isFavorite == oldItem.isFavorite

    override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean =
        oldItem == newItem
}