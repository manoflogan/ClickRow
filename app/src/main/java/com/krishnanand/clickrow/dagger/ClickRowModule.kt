package com.krishnanand.clickrow.dagger

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import com.krishnanand.clickrow.activity.ClickRowActivity
import com.krishnanand.clickrow.activity.ClickRowActivityModule
import com.krishnanand.clickrow.repository.ClickRowRepository
import com.krishnanand.clickrow.repository.ClickRowRepositoryImpl
import com.squareup.moshi.Moshi
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.Multibinds

@Module
interface ClickRowModule {

    @ContributesAndroidInjector(modules = [ClickRowActivityModule::class])
    fun contributeClickRowActivity(): ClickRowActivity

    @Binds
    fun bindContext(application: Application): Context

    @Multibinds
    fun bindViewModels(): Map<Class<out ViewModel>, ViewModel>

    @Binds
    fun bindClickRowRepository(instance: ClickRowRepositoryImpl): ClickRowRepository

    companion object {
        @Provides
        fun provideMoshiBuilder(): Moshi.Builder = Moshi.Builder()
    }
}