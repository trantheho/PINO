package com.example.loginpino.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import com.blankj.utilcode.util.ScreenUtils
import com.example.loginpino.R

class DialogLoading {

    private lateinit var mDialog: Dialog

    fun DialogLoading(mContext: Context){
        val width: Int = ScreenUtils.getScreenWidth()
        val height: Int = ScreenUtils.getScreenHeight()
        mDialog = Dialog(mContext, R.style.AppTheme)
        mDialog.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val view = LayoutInflater.from(mContext).inflate(R.layout.dialog_loading, null)
        val params = mDialog.getWindow()!!.getAttributes()
        params.width = width
        params.height = height// - BarUtils.getStatusBarHeight();
        view.setLayoutParams(params)
        mDialog.setContentView(view)
        mDialog.setCanceledOnTouchOutside(false)
        mDialog.setCancelable(false)

    }

    fun dismiss() {
        if (mDialog != null) {
            mDialog.dismiss()
        }
    }

    fun isShowing(): Boolean {
        return mDialog.isShowing
    }

    fun isGone(): Boolean {
        return !isShowing()
    }

}