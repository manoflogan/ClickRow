package com.krishnanand.clickrow

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner

class ClickRowTestRunner: AndroidJUnitRunner() {

    override fun newApplication(
        cl: ClassLoader?,
        className: String?,
        context: Context?
    ): Application {
        return super.newApplication(cl, ClickRowTestApplication::class.java.name, context)
    }
}