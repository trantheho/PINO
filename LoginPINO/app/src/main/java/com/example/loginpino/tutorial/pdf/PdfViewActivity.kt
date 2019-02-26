package com.example.loginpino.tutorial.pdf

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.telecom.Call
import com.example.loginpino.AppUtil
import com.example.loginpino.utils.DialogLoading
import com.github.barteksc.pdfviewer.PDFView
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.io.File
import java.util.*
import org.reactivestreams.Subscriber
import java.io.IOException




class PdfViewActivity : AppCompatActivity() {

    private lateinit var mDialogLoading: DialogLoading
    private  var title: String? = ""
    private lateinit var pdfFolder: File
    private lateinit var pdfView: PDFView
    private var link: String? = ""
    private lateinit var outputFile: File
    private var disposable: CompositeDisposable = CompositeDisposable()
    private var pdfViewActivity: PdfActivityViewModel = PdfActivityViewModel()


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

    private fun requestAndroidVersion() {
        pdfViewActivity.downloadFile()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                //cú pháp của rxjava trong kotlin
                { result ->
                    //request thành công
                    handleSuccessAndroidVersion(result)
                },
                { error ->
                    //request thất bai
                    handlerErrorAndroidVersion(error)
                }
            )
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