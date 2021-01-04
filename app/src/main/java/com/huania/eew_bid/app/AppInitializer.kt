package com.huania.eew_bid.app

import android.content.Context
import com.baidu.mapapi.CoordType
import com.baidu.mapapi.SDKInitializer
import com.dianping.logan.Logan
import com.dianping.logan.LoganConfig
import com.huania.eew_bid.data.db.AppDataBase
import com.huania.eew_bid.data.db.CityEntity
import com.huania.eew_bid.data.db.CountyEntity
import com.huania.eew_bid.data.db.ProvinceEntity
import com.huania.eew_bid.ext.SpeechAppID
import com.huania.eew_bid.utils.log.logD
import com.iflytek.cloud.SpeechConstant
import com.iflytek.cloud.SpeechUtility
import com.liulishuo.filedownloader.FileDownloader
import com.rousetime.android_startup.AndroidStartup
import com.rousetime.android_startup.Startup
import com.tencent.mmkv.MMKV
import interfaces.heweather.com.interfacesmodule.view.HeConfig
import org.koin.core.context.startKoin
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader


class StartUp1 : AndroidStartup<String>() {
    //用来控制create()方法调时所在的线程，返回true代表在主线程执行
    override fun callCreateOnMainThread() = true

    //组件初始化方法，执行需要处理的初始化逻辑，支持返回一个T类型的实例
    override fun create(context: Context): String? {
        //初始化SP
        logD("rootDir:${MMKV.initialize(context)}")

        // 初始化日志组件
        val config = LoganConfig.Builder()
            .setCachePath(context.cacheDir!!.absolutePath)
            .setPath(
                (context.getExternalFilesDir("Log")!!.absolutePath
                        + File.separator) + "logan_v1"
            )
            .setEncryptKey16("0123456789012345".toByteArray())
            .setEncryptIV16("0123456789012345".toByteArray())
            .build()
        Logan.init(config)

        startKoin {
            modules(appModule)
        }
        return ""
    }

    // 用来控制当前初始化的组件是否需要在主线程进行等待其完成
    override fun waitOnMainThread() = false

    // 用来表示当前组件在执行之前需要依赖的组件
    override fun dependencies(): List<Class<out Startup<*>>>? {
        return super.dependencies()
    }

}

class StartUpAddress : AndroidStartup<String>() {
    override fun callCreateOnMainThread() = false

    override fun create(context: Context): String? {
        var provinces =
            AppDataBase.getInstance(context).provinceDao().getAll()
        logD("provincesSize:  ${provinces.size}")
        if (provinces == null || provinces.size < 30) {
            var manager = context.resources.assets
            val inputStream = manager.open("addr.json")
            //把文件内容读取进缓冲读取器（use方法会自动对BufferedReader进行关闭）
            val provinceDao = AppDataBase.getInstance().provinceDao()
            val cityDao = AppDataBase.getInstance().cityDao()
            val countyDao = AppDataBase.getInstance().countyDao()
            BufferedReader(InputStreamReader(inputStream)).use {
                var line: String
                while (true) {
                    line = it.readLine() ?: break //当有内容时读取一行数据，否则退出循环
                    logD("正在读取地址信息:$line")
                    val data = line.split("-")
                    if (data.size == 6) {
                        val provinceCode = data[0].substring(0, 2)
                        val cityCode = data[0].substring(0, 4)
                        val countyCode = data[0].substring(0, 6)
                        if (provinceDao.get(provinceCode).isEmpty()) {
                            provinceDao.insert(ProvinceEntity(provinceCode, data[3]))
                        }
                        if (cityDao.get(cityCode).isEmpty()) {
                            cityDao.insert(CityEntity(cityCode, provinceCode, data[4]))
                        }
                        if (countyDao.get(countyCode).isEmpty()) {
                            countyDao.insert(CountyEntity().apply {
                                code = countyCode
                                name = data[5]
                                parentCode = cityCode
                                lat = data[2].toFloat()
                                lng = data[1].toFloat()
                            })
                        }
                    }
                }
            }
        }
        return ""
    }

    override fun waitOnMainThread() = false

    override fun dependencies(): List<Class<out Startup<*>>>? {
        return super.dependencies()
    }

}