package com.krishnanand.clickrow.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider

class InjectionViewModelFactory @Inject constructor(
    private val viewModelProviderMap: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
): ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val viewModelProvider = viewModelProviderMap[modelClass] ?: throw IllegalArgumentException("view model class binding not found")
        return viewModelProvider.get() as T
    }
}