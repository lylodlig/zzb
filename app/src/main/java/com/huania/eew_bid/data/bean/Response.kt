package com.huania.eew_bid.data.bean

data class Response<out T>(val code: Int, val msg: String, val data: T)