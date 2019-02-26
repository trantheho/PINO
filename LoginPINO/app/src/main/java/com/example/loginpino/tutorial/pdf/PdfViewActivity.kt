package com.example.loginpino.tutorial.pdf

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.loginpino.AppUtil
import com.example.loginpino.utils.DialogLoading
import com.github.barteksc.pdfviewer.PDFView
import com.tbruyelle.rxpermissions2.RxPermissions
import java.io.File

class PdfViewActivity : AppCompatActivity() {

    lateinit var mDialogLoading: DialogLoading
    internal var title: String? = ""
    internal lateinit var pdfFolder: File
    internal lateinit var pdfView: PDFView
    internal var link: String? = ""
    internal lateinit var outputFile: File

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

        RxPermissions(this@PdfViewActivity).request(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
            .subscribe({ granted ->
                if (granted) {
                    DownloadingTask().execute(link)
                } else {
                    AppUtil.toast("Để thực hiện thao tác, vui lòng cấp quyền cho ứng dụng")
                }
            })
    }

    private fun openPdfDocument(file: File) {
        pdfView.fromUri(Uri.fromFile(file))
                .load()
    }

    private fun getLinkName(link: String): String {
        val nameLink = link.split("/".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        return nameLink[nameLink.size - 1]
    }



    override fun onDestroy() {
        super.onDestroy()
        if (outputFile != null)
            deleteFile(outputFile)
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