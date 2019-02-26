package com.example.loginpino.tutorial

import android.os.Environment

class CheckForSDCard {
    fun isSDCardPresent(): Boolean {
        return if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) true else false
    }
}