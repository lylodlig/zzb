package com.huania.eew_bid.utils.log

import android.util.Log
import com.blankj.utilcode.util.TimeUtils
import com.dianping.logan.Logan
import java.util.*

private var debug = true
private const val TAG: String = "huania"
var isLoganEnable = false

fun logE(text: String) {
    var t = "${TimeUtils.date2String(Date())}----------$text"
    if (isLoganEnable) {
        Logan.w(t, 5)
    }
    if (debug)
        Log.e(TAG, "$t")
}

fun logI(text: String) {
    var t = "${TimeUtils.date2String(Date())}----------$text"
    if (isLoganEnable) {
        Logan.w(t, 4)
    }
    if (debug)
        Log.i(TAG, "$t")
}

fun logD(text: String) {
    var t = "${TimeUtils.date2String(Date())}----------$text"
    if (isLoganEnable) {
        Logan.w(t, 3)
    }
    if (debug)
        Log.d(TAG, "$t")
}

fun logV(text: String) {
    var t = "${TimeUtils.date2String(Date())}----------$text"
    if (isLoganEnable) {
        Logan.w(t, 2)
    }
    if (debug)
        Log.v(TAG, "$t")
}