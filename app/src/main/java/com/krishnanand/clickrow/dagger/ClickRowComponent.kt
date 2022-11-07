package com.krishnanand.clickrow.dagger

import android.app.Application
import com.krishnanand.clickrow.ClickRowApplication
import com.krishnanand.clickrow.repository.CoroutineDispatcherModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        ViewModelInjectionModule::class,
        ClickRowModule::class,
        CoroutineDispatcherModule::class
    ]
)
interface ClickRowComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): ClickRowComponent
    }

    fun inject(application: ClickRowApplication)
}