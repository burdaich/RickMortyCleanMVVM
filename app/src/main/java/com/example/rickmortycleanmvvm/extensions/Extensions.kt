package com.example.rickmortycleanmvvm.extensions

import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.amrdeveloper.lottiedialog.LottieDialog
import com.example.rickmortycleanmvvm.R
import com.example.rickmortycleanmvvm.model.ErrorType
import com.example.rickmortycleanmvvm.model.base.BaseState
import com.google.android.material.snackbar.Snackbar

fun <T> Fragment.observeResponse(
    it: BaseState<T>,
    loadingDialog: LottieDialog,
    showLoadingDialog: Boolean? = false,
    funcError: (() -> Unit)? = null,
    funcSuccess: (() -> Unit)? = null,
) {
    when {
        it.isLoading -> {
            if (showLoadingDialog == true && !loadingDialog.isShowing) {
                loadingDialog.show()
            }
        }
        it.data != null -> {
            if (showLoadingDialog == true) loadingDialog.dismiss()
            funcSuccess?.let { it() }
        }
        it.error != null -> {
            if (showLoadingDialog == true) loadingDialog.dismiss()
            handleError(it.error, funcError)
        }
    }
}

fun Fragment.handleError(errorType: ErrorType, func: (() -> Unit)? = null) {
    when (errorType) {
        is ErrorType.IsQueryError -> func?.let { it() }

        ErrorType.IsNetworkError -> {
            showSnackbar(
                "Please check your internet connection"
            )
        }
    }
}

private fun Fragment.showSnackbar(
    message: String
) {
    val snackBar = Snackbar.make(
        requireActivity().findViewById(android.R.id.content),
        message, Snackbar.LENGTH_LONG
    )
    snackBar.view.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.yellow))
    snackBar.show()
}