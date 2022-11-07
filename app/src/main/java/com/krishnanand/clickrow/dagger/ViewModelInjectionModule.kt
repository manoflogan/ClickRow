package com.krishnanand.clickrow.dagger

import androidx.lifecycle.ViewModelProvider
import com.krishnanand.clickrow.viewmodels.InjectionViewModelFactory
import dagger.Binds
import dagger.Module

/**
 * Module
 */
@Module
interface ViewModelInjectionModule {

    @Binds
    fun bindViewModelProviderFactory(instance: InjectionViewModelFactory): ViewModelProvider.Factory
}