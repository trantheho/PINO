package com.example.loginpino.tutorial.pdf

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.loginpino.utils.DialogLoading
import com.github.barteksc.pdfviewer.PDFView
import com.example.loginpino.R
import kotlinx.android.synthetic.main.activity_pdf_view.*
import android.os.AsyncTask.execute
import com.blankj.utilcode.util.NetworkUtils.isConnected
import android.net.NetworkInfo
import android.content.Context.CONNECTIVITY_SERVICE
import android.support.v4.content.ContextCompat.getSystemService
import android.net.ConnectivityManager
import com.blankj.utilcode.util.SnackbarUtils.dismiss
import android.content.DialogInterface
import android.support.v4.os.HandlerCompat.postDelayed
import android.databinding.adapters.SeekBarBindingAdapter.setProgress
import android.widget.ProgressBar
import android.os.AsyncTask
import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.os.Handler
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.Toast
import com.krishna.fileloader.FileLoader
import com.krishna.fileloader.listener.FileRequestListener
import com.krishna.fileloader.pojo.FileResponse
import com.krishna.fileloader.request.FileLoadRequest

import okhttp3.OkHttpClient
import okhttp3.Request
import okio.Okio
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class PdfViewActivity : AppCompatActivity() {

     var mDialogLoading: DialogLoading = DialogLoading()
     private var title: String = ""
    private var link: String? = ""




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
        loadPDF(link.toString())
    }

    fun loadPDF(link : String)
    {
        FileLoader.with(this)
                .load(link, false)
                .fromDirectory("PDF file", FileLoader.DIR_INTERNAL)
                .asFile(object : FileRequestListener<File>
                {
                    override fun onLoad(p0: FileLoadRequest?, p1: FileResponse<File>?) {
                        progressBar.visibility = View.VISIBLE
                        val file = p1!!.body

                        pdfView.fromFile(file)
                                .password(null)
                                .defaultPage(0)
                                .enableSwipe(true)
                                .swipeHorizontal(false)
                                .enableDoubletap(true)
                                .onDraw { canvas, pageWidth, pageHeight, displayedPage ->  }
                                .onDrawAll { canvas, pageWidth, pageHeight, displayedPage ->  }
                                .onPageChange { page, pageCount ->  }
                                .onPageError { page, t ->
                                    Toast.makeText(this@PdfViewActivity, "Error when open link"+page, Toast.LENGTH_SHORT).show()

                                }
                                .onTap { false }
                                .onRender { nbPages, pageWidth, pageHeight ->  }
                                .enableAnnotationRendering(true)
                                .invalidPageColor(Color.RED)
                                .load()

                    }

                    override fun onError(p0: FileLoadRequest?, p1: Throwable?) {
                       Toast.makeText(this@PdfViewActivity, ""+p1!!.message, Toast.LENGTH_SHORT).show()
                        progressBar.visibility = View.GONE
                    }

                })

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