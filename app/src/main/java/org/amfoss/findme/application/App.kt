package org.amfoss.findme.application

import android.app.Application
import android.os.Build
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        context = this
    }

    companion object {
        var context: App? = null
    }
}