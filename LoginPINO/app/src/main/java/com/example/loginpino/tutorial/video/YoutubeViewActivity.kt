package com.example.loginpino.tutorial.video

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.DisplayMetrics
import android.widget.FrameLayout
import android.widget.ImageView
import com.example.loginpino.R
import kotlinx.android.synthetic.main.fragment_youtube.*

class YoutubeViewActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_youtube)
        iv_Back.setOnClickListener({ view -> finish() })
        setVideoSquare()
        val fragamentYoutube = FragamentYoutube.newInstance("luz3NBUtExU")
        supportFragmentManager.beginTransaction().replace(R.id.framelayout_video_recipe_detail, fragamentYoutube)
            .commit()
    }

    private fun setVideoSquare() {
        val mainVideoLayout = findViewById<FrameLayout>(R.id.framelayout_video_recipe_detail)
        val layoutParams = mainVideoLayout.layoutParams
        val videoHeight = widthPixels(this)
        layoutParams.height = videoHeight
        mainVideoLayout.layoutParams = layoutParams
    }

    fun widthPixels(context: Context): Int {
        val displayMetrics = DisplayMetrics()
        (context as AppCompatActivity).windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.widthPixels
    }
}