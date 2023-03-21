package com.kakaobank.imagesearch.util.extension

import android.util.Log

object KakaoLog {
    private fun buildTag(): String =
        Thread.currentThread().stackTrace[4].let { ste ->
            "${ste.fileName}:${ste.lineNumber}#${ste.methodName}"
        }

    fun d(message: String) = Log.d(buildTag(), message)

    fun v(message: String) = Log.v(buildTag(), message)

    fun i(message: String) = Log.i(buildTag(), message)

    fun w(message: String) = Log.w(buildTag(), message)

    fun e(message: String) = Log.e(buildTag(), message)

    fun e(message: String = "", throwable: Throwable) = Log.e(buildTag(), message, throwable)
}



