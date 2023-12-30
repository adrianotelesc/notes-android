package com.adrianotelesc.postnote

import android.app.Application
import com.adrianotelesc.postnote.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class PostnoteApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin()
    }

    private fun startKoin() {
        startKoin {
            androidLogger()
            androidContext(this@PostnoteApp)
            modules(modules = appModule)
        }
    }
}
