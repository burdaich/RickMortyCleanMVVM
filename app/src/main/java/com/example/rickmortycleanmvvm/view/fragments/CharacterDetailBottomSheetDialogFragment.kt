package com.example.rickmortycleanmvvm.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.domain.model.Character
import com.example.rickmortycleanmvvm.databinding.CharacterDetailBottomSheetBinding
import com.example.rickmortycleanmvvm.viewmodel.CharacterDetailViewModel
import com.example.rickmortycleanmvvm.viewmodel.CharactersViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CharacterDetailBottomSheetDialogFragment(private val character: Character) :
    BottomSheetDialogFragment() {

    private lateinit var viewModel: CharacterDetailViewModel
    private lateinit var binding: CharacterDetailBottomSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = CharacterDetailBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[CharacterDetailViewModel::class.java]
        addDataToUI()
        buildListeners()
    }

    private fun buildListeners() {
        binding.backArrowIB.setOnClickListener { dismiss() }
        binding.characterDetailAddToFavoritesB.setOnClickListener {
            viewModel.addCharacterToFavorite(
                character
            )
        }
    }

    private fun addDataToUI() {
        Glide.with(requireContext()).load(character.image).into(binding.characterDetailCIV)
        binding.characterDetailNameTV.text = character.name
        binding.characterDetailStatusTV.text = character.status
        binding.characterDetailSpeciesTV.text = character.species
        binding.characterDetailGenderTV.text = character.gender
    }

    companion object {
        fun show(fragmentManager: FragmentManager, character: Character) =
            CharacterDetailBottomSheetDialogFragment(character).show(
                fragmentManager, this::
                class.java.name
            )
    }
}