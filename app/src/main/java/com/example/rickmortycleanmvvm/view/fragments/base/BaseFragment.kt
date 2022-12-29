package com.example.rickmortycleanmvvm.view.fragments.base

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.amrdeveloper.lottiedialog.LottieDialog
import com.example.rickmortycleanmvvm.R

abstract class BaseFragment<VM : ViewModel> : Fragment() {
    protected lateinit var viewModel: VM
    protected lateinit var loadingDialog: LottieDialog
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadingDialog = lottieDialog()
    }

    fun Fragment.onFragmentBackPressed() {
        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().popBackStack()
                }
            })
    }

    private fun Fragment.lottieDialog(): LottieDialog = LottieDialog(this.requireContext())
        .setAnimation(R.raw.loadinganimation)
        .setAnimationRepeatCount(LottieDialog.INFINITE)
        .setCancelable(false)
        .setDialogHeight(resources.getDimensionPixelSize(R.dimen.lottieHeight))
        .setDialogWidth(resources.getDimensionPixelSize(R.dimen.lottieWidth))
        .setAutoPlayAnimation(true)
}