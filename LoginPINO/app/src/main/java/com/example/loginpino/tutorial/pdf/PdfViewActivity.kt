package com.example.loginpino.tutorial.pdf

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.os.Environment
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.example.loginpino.utils.AppUtil
import com.example.loginpino.tutorial.CheckForSDCard
import com.example.loginpino.utils.DialogLoading
import com.github.barteksc.pdfviewer.PDFView
import com.tbruyelle.rxpermissions2.RxPermissions
import java.net.HttpURLConnection
import java.net.URL
import android.content.Context.MODE_PRIVATE
import com.blankj.utilcode.util.FileIOUtils
import com.blankj.utilcode.util.Utils
import com.github.barteksc.pdfviewer.util.Util
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
        mDialogLoading.DialogLoading(this)
        title = intent.extras.getString("title")
        link = intent.extras.getString("link")
        //PDF_tittle_bar.text = "Chức năng mới"

        Toast.makeText(this, getLinkName(link.toString()), Toast.LENGTH_LONG).show()

        DownloadingTask().execute("http://gahp.net/wp-content/uploads/2017/09/sample.pdf")
//        RxPermissions(this@PdfViewActivity).request(
//            Manifest.permission.READ_EXTERNAL_STORAGE,
//            Manifest.permission.WRITE_EXTERNAL_STORAGE
//        )
//            .subscribe({ granted ->
//                if (granted) {
//
//                } else {
//                    AppUtil.toast("Để thực hiện thao tác, vui lòng cấp quyền cho ứng dụng")
//                }
//            })
    }


    private fun openPdfDocument(inputStream: InputStream) {
       pdfView.fromStream(inputStream).load()
    }

    fun getLinkName(link: String): String {
        val nameLink = link.split("/".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        return nameLink[nameLink.size - 1]
    }

    inner class DownloadingTask : AsyncTask<String, Void, InputStream>() {

        override fun onPostExecute(result: InputStream) {
          try {
                  //openPdfDocument(result)
                  pdfView.fromStream(result).load()
          }
          catch (e: Exception)
          {
              e.printStackTrace()
              mDialogLoading.dismiss()

          }
            super.onPostExecute(result)
        }

        override fun onPreExecute() {
            super.onPreExecute()
            mDialogLoading.show()

        }

        override fun doInBackground(vararg arg0: String): InputStream? {
            var inputStream: InputStream? = null
            try {
                val url = URL(arg0[0])//Create Download URl
                val c = url.openConnection() as HttpURLConnection//Open Url Connection
                c.connect()
                if (c.responseCode == 200)
                {
                    inputStream = BufferedInputStream(c.inputStream)
                }
            }catch (e: IOException)
            {
                return null
            }

            return inputStream
        }

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