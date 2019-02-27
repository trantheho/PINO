package com.example.loginpino.utils

import android.content.Context
import android.content.res.Resources
import android.graphics.Point
import android.graphics.drawable.Drawable
import android.os.Handler
import android.os.Looper
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.text.TextUtils
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import com.example.loginpino.Myapp

class AppUtil private constructor() {

    init {
        throw UnsupportedOperationException("Not allow instantiating object.")
    }

    companion object {

        private val sDisplaySize = Point(0, 0)

        private val sUIHandler = Handler(Looper.getMainLooper())

        var sDensity: Float = 0.toFloat()

        init {
            sDensity = Myapp.instance.getResources().getDisplayMetrics().density
        }

        fun dp(dp: Float): Int {
            return if (dp == 0f) {
                0
            } else Math.ceil((sDensity * dp).toDouble()).toInt()
        }

        fun dp2(dp: Float): Int {
            return if (dp == 0f) {
                0
            } else Math.floor((sDensity * dp).toDouble()).toInt()
        }

        fun dp2px(dp: Float): Float {
            return sDensity * dp
        }

        fun getDisplaySize(context: Context): Point {
            if (sDisplaySize.x === 0 && sDisplaySize.y === 0) {
                val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
                wm.getDefaultDisplay().getSize(sDisplaySize)
            }
            return Point(sDisplaySize)
        }

        fun toast(s: String, vararg arguments: Any) {
            if (TextUtils.isEmpty(s)) {
                return
            }
            val message = String.format(s, *arguments)
            if (isOnMainThread) {
                Toast.makeText(Myapp.instance.getApplicationContext(), message, Toast.LENGTH_SHORT).show()
            } else {
                sUIHandler.post({
                    Toast.makeText(Myapp.instance.getApplicationContext(), message, Toast.LENGTH_SHORT).show()
                })
            }
        }

        fun toast(@StringRes res: Int, vararg arguments: Any) {
            try {
                val message = String.format(getString(res), *arguments)
                if (isOnMainThread) {
                    Toast.makeText(Myapp.instance.getApplicationContext(), message, Toast.LENGTH_SHORT).show()
                } else {
                    sUIHandler.post({
                        Toast.makeText(
                            Myapp.instance.getApplicationContext(),
                            message,
                            Toast.LENGTH_SHORT
                        ).show()
                    })
                }
            } catch (e: Resources.NotFoundException) {
                e.printStackTrace()
            }

        }

        fun showSnackbar(coordinator: View, msgId: String, action: String, listener: View.OnClickListener?): Snackbar {
            val snackbar = Snackbar.make(
                coordinator,
                msgId, Snackbar.LENGTH_INDEFINITE
            )
            if (listener != null) {
                snackbar.setAction(action, listener)
            }

            snackbar.show()
            return snackbar
        }

        fun showSnackbar(coordinator: View, msgId: Int, action: Int, listener: View.OnClickListener?): Snackbar {
            val snackbar = Snackbar.make(
                coordinator,
                msgId, Snackbar.LENGTH_INDEFINITE
            )
            if (listener != null) {
                snackbar.setAction(action, listener)
            }

            snackbar.show()
            return snackbar
        }

        fun hideSnackbar(snackbar: Snackbar) {
            if (isSnackBarShowing(snackbar)) {
                snackbar.dismiss()
            }
        }

        fun isSnackBarShowing(snackbar: Snackbar?): Boolean {
            return snackbar != null && snackbar!!.isShown()
        }

        fun getString(@StringRes stringRes: Int): String {
            try {
                return Myapp.instance.getString(stringRes)
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return ""
        }

        val isOnMainThread: Boolean
            get() {
                try {
                    return Looper.myLooper() === Looper.getMainLooper()
                } catch (ignore: StackOverflowError) {
                    return false
                }

            }

        fun getDrawable(name: String): Drawable? {
            try {
                val resources = Myapp.instance.getResources()
                val resourceId = resources.getIdentifier(
                    name, "drawable",
                    Myapp.instance.getPackageName()
                )
                return resources.getDrawable(resourceId)
            } catch (e: android.content.res.Resources.NotFoundException) {
                return null
            }

        }

        fun getResourceId(name: String): Int {
            try {
                return Myapp.instance.getResources()
                    .getIdentifier(name, "drawable", Myapp.instance.getPackageName())
            } catch (e: android.content.res.Resources.NotFoundException) {
                return 0
            }

        }
    }
}