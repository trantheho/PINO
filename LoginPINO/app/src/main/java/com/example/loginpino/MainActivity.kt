package com.example.loginpino

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.blankj.utilcode.util.ActivityUtils
import com.example.loginpino.tutorial.TutorialActivity
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var compositeDisposable: CompositeDisposable
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_tutorial.setOnClickListener{
            routeToTutorialActivity()
        }

        btnfb.setOnClickListener{
            onNavigateFacebook()
        }

        btncall.setOnClickListener{
            onCall()
        }



    }

    private fun routeToTutorialActivity()
    {
        var intent = Intent(this, TutorialActivity::class.java)
        startActivity(intent)
    }

     private fun onCall() {
        val disposable = RxPermissions(this).request(Manifest.permission.CALL_PHONE)
                .subscribe { granted ->
                    if (granted!!) {
                        onCall()
                    }
                }
        compositeDisposable.add(disposable)
    }

    private fun onNavigateFacebook() {
        val  url = "https://www.facebook.com/groups/248531216020755/"
        try {
            val appInfo = packageManager.getApplicationInfo("com.facebook.katana", 0)
            if (appInfo.enabled) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("fb://facewebmodal/f?href=" + url))
                ActivityUtils.startActivity(intent)
            }
        } catch (ignored: Exception) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            ActivityUtils.startActivity(intent)
        }

    }
}

