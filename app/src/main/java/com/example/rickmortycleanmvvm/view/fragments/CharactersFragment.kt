package com.example.rickmortycleanmvvm.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.domain.model.Character
import com.example.rickmortycleanmvvm.adapter.CharactersAdapter
import com.example.rickmortycleanmvvm.databinding.FragmentCharactersBinding
import com.example.rickmortycleanmvvm.viewmodel.CharactersViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharactersFragment : BaseFragment<CharactersViewModel>() {
    private lateinit var binding: FragmentCharactersBinding
    private lateinit var charactersAdapter: CharactersAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharactersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this)[CharactersViewModel::class.java]
        super.onViewCreated(view, savedInstanceState)

        buildAdapter()
        observeCharactersResponse()
        buildListeners()
    }

    private fun buildListeners() {
        binding.charactersSRL.setOnRefreshListener {
            viewModel.getCharacters()
        }

        binding.charactersFindET.addTextChangedListener {
            viewModel.getCharactersByName(it.toString())
        }
    }

    private fun buildAdapter() {
        charactersAdapter = CharactersAdapter(::callback)
        binding.charactersRV.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = charactersAdapter
        }
    }

    private fun observeCharactersResponse() {
        viewModel.state.observe(viewLifecycleOwner) {
            if (!it.isLoading) {
                if (it.error.isBlank()) {
                    binding.charactersSRL.isRefreshing = false
                    charactersAdapter.submitList(it.characters?.characters)
                }
            }
        }
    }

    private fun callback(character: Character) {
            CharacterDetailBottomSheetDialogFragment.show(
                childFragmentManager,
                character
            )
    }
}