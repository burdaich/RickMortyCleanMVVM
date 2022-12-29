package com.example.rickmortycleanmvvm.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.domain.model.Character
import com.example.rickmortycleanmvvm.adapter.CharactersAdapter
import com.example.rickmortycleanmvvm.databinding.FragmentCharactersBinding
import com.example.rickmortycleanmvvm.view.fragments.base.BaseFragment
import com.example.rickmortycleanmvvm.viewmodel.CharactersViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

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
        observeAdapterLoadStates()
        viewModel.getCharactersByName()
    }

    private fun buildListeners() {
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
        lifecycleScope.launch {
            viewModel.state.collect {
                charactersAdapter.submitData(viewLifecycleOwner.lifecycle, it)
            }
        }
    }

    private fun observeAdapterLoadStates() {
        lifecycleScope.launch {
            charactersAdapter.loadStateFlow.collectLatest {
                Log.d("TAG", it.refresh.toString())
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