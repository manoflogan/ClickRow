package com.krishnanand.clickrow

import android.app.Application
import com.krishnanand.clickrow.dagger.ClickRowModule
import com.krishnanand.clickrow.dagger.ViewModelInjectionModule
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
interface ClickRowTestComponent {

    fun inject(clickRowTestApplication: ClickRowTestApplication)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): ClickRowTestComponent
    }
}