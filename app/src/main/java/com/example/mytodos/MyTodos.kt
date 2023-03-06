package com.example.mytodos

import android.app.Application
import com.example.mytodos.di.myApplicationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyTodos : Application(){

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MyTodos)
           modules(listOf(myApplicationModule))
        }
    }
}