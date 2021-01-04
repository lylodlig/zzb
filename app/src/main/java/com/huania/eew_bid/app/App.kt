package com.huania.eew_bid.app

import android.app.Application
import android.content.Context
import android.media.AudioManager
import com.huania.eew_bid.data.db.EarthquakeEntity
import java.util.*

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: Context
        var isMainServerOnline = false
        var isAuxiServerOnline = false
        var isManagerServerOnline = false

        // 存储已触发地震
        var listQuake = mutableListOf<EarthquakeEntity>()

        // 存储用于语音播报的顺序地震
        var listPlay = mutableListOf<EarthquakeEntity>()

        val mapCountdown = HashMap<Long, EarthquakeEntity>()

        fun openVol() {
            val audioManager = instance.getSystemService(Context.AUDIO_SERVICE) as AudioManager
            audioManager.isMicrophoneMute = true
            audioManager.isSpeakerphoneOn = false
        }

        fun closeVol() {
            val audioManager = instance.getSystemService(Context.AUDIO_SERVICE) as AudioManager
            audioManager.isMicrophoneMute = false
            audioManager.isSpeakerphoneOn = true
        }
    }


}