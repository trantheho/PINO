package com.example.loginpino

import android.app.Application
import com.ivankocijan.magicviews.MagicViews

class Myapp : Application(){



    override fun onCreate() {
        super.onCreate()
        instance = this
        MagicViews.setFontFolderPath(this, "fonts")
    }

    fun getInstance(): Myapp
    {
        return instance
    }companion object {
        lateinit var instance: Myapp
            private set
    }
}