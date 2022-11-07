package com.krishnanand.clickrow

import android.app.Application
import com.krishnanand.clickrow.dagger.DaggerClickRowComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class ClickRowApplication: Application(), HasAndroidInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector(): AndroidInjector<Any>  = dispatchingAndroidInjector

    override fun onCreate() {
        super.onCreate()
        DaggerClickRowComponent.factory().create(this).inject(this)
    }
}