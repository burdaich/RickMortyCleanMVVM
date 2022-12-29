package com.example.rickmortycleanmvvm.view.fragments.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.amrdeveloper.lottiedialog.LottieDialog
import com.example.rickmortycleanmvvm.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class BaseBottomSheetDialogFragment<VM : ViewModel> : BottomSheetDialogFragment() {
    protected lateinit var viewModel: VM
    protected lateinit var loadingDialog: LottieDialog

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadingDialog = lottieDialog()
    }

    private fun Fragment.lottieDialog(): LottieDialog = LottieDialog(this.requireContext())
        .setAnimation(R.raw.loadinganimation)
        .setAnimationRepeatCount(LottieDialog.INFINITE)
        .setCancelable(false)
        .setDialogHeight(resources.getDimensionPixelSize(R.dimen.lottieHeight))
        .setDialogWidth(resources.getDimensionPixelSize(R.dimen.lottieWidth))
        .setAutoPlayAnimation(true)
}