package com.example.loginpino.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import com.blankj.utilcode.util.ScreenUtils
import com.example.loginpino.R
import com.github.florent37.shapeofview.shapes.RoundRectView

class DialogLoading {

    lateinit var mDialog: Dialog
    val mViewLoading: RoundRectView? = null

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

    fun show() {
        if (mDialog != null) {
            if (mViewLoading != null) {
                mViewLoading.setVisibility(View.VISIBLE)
                mViewLoading.setAlpha(0f)
                mViewLoading.setScaleX(0f)
                mViewLoading.setScaleY(0f)
                mViewLoading.animate()
                    .alpha(1f)
                    .scaleX(1f).scaleY(1f)
                    .setDuration(350)
                    .start()
            }
            mDialog.show()
        }
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