package com.krishnanand.clickrow.activity

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import com.krishnanand.clickrow.dagger.ViewModelInjectionModule
import com.krishnanand.clickrow.viewmodels.ClickRowViewModel
import com.krishnanand.clickrow.viewmodels.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ClickRowActivityModule {

    @[Binds IntoMap ViewModelKey(ClickRowViewModel::class)]
    fun bindClickRowViewModel(instance: ClickRowViewModel): ViewModel

    @Binds
    fun bindClickRowActivity(activity: ClickRowActivity): AppCompatActivity
}