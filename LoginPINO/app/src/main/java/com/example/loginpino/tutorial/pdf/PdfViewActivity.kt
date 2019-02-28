package com.example.loginpino.tutorial.pdf

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import com.example.loginpino.utils.DialogLoading
import com.github.barteksc.pdfviewer.PDFView
import com.example.loginpino.R
import kotlinx.android.synthetic.main.activity_pdf_view.*
import java.io.*


class PdfViewActivity : AppCompatActivity() {

     var mDialogLoading: DialogLoading = DialogLoading()
     private var title: String = ""
    private lateinit var pdfFolder: File
    private lateinit var pdfView: PDFView
    private var link: String? = ""
    var outputFile: File? = null



    fun startActivity(activity: Activity, title: String, link: String) {
        val intent = Intent(activity, PdfViewActivity::class.java)
        val b = Bundle()
        b.putString("title", title)
        b.putString("link", link)
        intent.putExtras(b)
        activity.startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdf_view)
        mDialogLoading.DialogLoading(this)
        title = intent.extras.getString("title")
        link = intent.extras.getString("link")
        PDF_tittle_bar.text = title


        webview.settings.javaScriptEnabled
        webview!!.loadUrl( "http://docs.google.com/gview?embedded=true&url="+ link)
    }

    fun getLinkName(link: String): String {
        val nameLink = link.split("/".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        return nameLink[nameLink.size - 1]
    }

    override fun onDestroy() {
        super.onDestroy()
        if (outputFile != null)
            deleteFile(outputFile!!)
    }

    private fun deleteFile(file: File): Boolean {
        //If file is exists
        if (file.exists()) {
            if (file.delete()) {
                deleteCache()
            }
        }
        return true
    }

    private fun deleteCache() {
        try {
            val dir = this.cacheDir
            deleteDir(dir)
        } catch (e: Exception) {
        }

    }

    private fun deleteDir(dir: File?): Boolean {
        if (dir != null && dir.isDirectory) {
            val children = dir.list()
            for (i in children.indices) {
                val success = deleteDir(File(dir, children[i]))
                if (!success) {
                    return false
                }
            }
            return dir.delete()
        } else return if (dir != null && dir.isFile) {
            dir.delete()
        } else {
            false
        }
    }
}