package com.huania.eew_bid.data.api

import android.annotation.SuppressLint
import com.huania.eew_bid.BuildConfig
import com.huania.eew_bid.utils.log.logD
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.ResponseBody.Companion.toResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

abstract class BaseRetrofitClient {

    companion object {
        private const val TIME_OUT = 5
    }

    private val client: OkHttpClient
        get() {
            val builder = OkHttpClient.Builder()
            val logging = HttpLoggingInterceptor()
            if (BuildConfig.DEBUG) {
                logging.level = HttpLoggingInterceptor.Level.BODY
            } else {
                logging.level = HttpLoggingInterceptor.Level.BASIC
            }
            builder.addInterceptor(logging)
                .addInterceptor(mLoggingInterceptor)
                .connectTimeout(TIME_OUT.toLong(), TimeUnit.SECONDS)
                .readTimeout(TIME_OUT.toLong(), TimeUnit.SECONDS)
            handleBuilder(builder)
            return builder.build()
        }

    @SuppressLint("BinaryOperationInTimber")
    private val mLoggingInterceptor = Interceptor { chain ->
        val request = chain.request()
        val t1 = System.nanoTime()
        val response = chain.proceed(chain.request())
        val t2 = System.nanoTime()
        val contentType = response.body?.contentType()
        val content = response.body?.string()
        logD("request url: + ${request.url} + ntime: + ${(t2 - t1) / 1e6} + nbody: + content \n")
        response.newBuilder()
            .body(content?.toResponseBody(contentType))
            .build()
    }

    protected abstract fun handleBuilder(builder: OkHttpClient.Builder)
    protected abstract fun retrofitBuilder(builder: Retrofit.Builder)

    fun <S> getService(serviceClass: Class<S>): S {
        val builder: Retrofit.Builder = Retrofit.Builder()
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("")
        retrofitBuilder(builder)
        return builder.build().create(serviceClass)
    }
}

