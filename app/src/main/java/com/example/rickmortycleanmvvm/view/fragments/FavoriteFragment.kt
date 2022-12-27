package com.example.rickmortycleanmvvm.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.domain.model.Character
import com.example.rickmortycleanmvvm.adapter.CharactersFavoritesAdapter
import com.example.rickmortycleanmvvm.databinding.FragmentFavoriteBinding
import com.example.rickmortycleanmvvm.viewmodel.FavoritesCharactersViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : BaseFragment<FavoritesCharactersViewModel>() {
    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var charactersFavoritesAdapter: CharactersFavoritesAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this)[FavoritesCharactersViewModel::class.java]
        super.onViewCreated(view, savedInstanceState)
        buildAdapter()
        observeCharactersFavoritesResponse()
        viewModel.getFavoritesCharacters()
    }

    private fun observeCharactersFavoritesResponse() {
        viewModel.state.observe(viewLifecycleOwner) {
            if (!it.isLoading) {
                if (it.error.isBlank()) {
                    charactersFavoritesAdapter.submitList(it.characters)
                    manageLayoutsVisibilities(it.characters?.isEmpty() ?: true)
                }
            }
        }
    }

    private fun manageLayoutsVisibilities(empty: Boolean) {
        binding.favoriteCharacterEmptyCL.isVisible = empty
        binding.favoritesCharacterDataCL.isVisible = !empty
    }

    private fun buildAdapter() {
        charactersFavoritesAdapter = CharactersFavoritesAdapter(this::callback)
        binding.favoritesCharactersRV.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            layoutManager.apply {
                textAlignment = GridLayout.TEXT_ALIGNMENT_CENTER
            }
            adapter = charactersFavoritesAdapter
        }
    }

    private fun callback(character: Character) {
        CharacterDetailBottomSheetDialogFragment.show(
            childFragmentManager,
            character, ::reloadData
        )
    }

    private fun reloadData() {
        viewModel.getFavoritesCharacters()
    }

    companion object {
        const val IS_FROM_FAVORITE = "isFromFavorite"
    }
}