package com.example.rickmortycleanmvvm.view.fragments

import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController

abstract class BaseFragment<VM : ViewModel> : Fragment() {
    protected lateinit var viewModel: VM

    fun Fragment.onFragmentBackPressed() {
        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().popBackStack()
                }
            })
    }
}