package com.example.rickmortycleanmvvm.viewmodel

import androidx.lifecycle.ViewModel
import com.example.domain.common.Resource
import com.example.rickmortycleanmvvm.model.ErrorType

abstract class BaseViewModel : ViewModel() {

    fun <T> getErrorType(resourceError: Resource.Error<T>): ErrorType =
        if (resourceError.isNetworkError == true) ErrorType.IsNetworkError else ErrorType.IsQueryError
    }