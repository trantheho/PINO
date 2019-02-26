package com.example.loginpino

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.loginpino.tutorial.TutorialActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_tutorial.setOnClickListener{
            routeToTutorialActivity()
        }
    }
    fun routeToTutorialActivity()
    {
        var intent = Intent(this, TutorialActivity::class.java)
        startActivity(intent)
    }
}
