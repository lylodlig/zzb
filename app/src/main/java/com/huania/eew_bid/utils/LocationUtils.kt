package com.huania.eew_bid.utils

import android.content.Context
import com.baidu.location.BDAbstractLocationListener
import com.baidu.location.BDLocation
import com.baidu.location.LocationClient
import com.baidu.location.LocationClientOption
import com.huania.eew_bid.utils.log.logD

class LocationUtils {
    private var locationClient: LocationClient? = null
    private var onLocationListener: OnLocationListener? = null

    private var listener = object : BDAbstractLocationListener() {
        override fun onReceiveLocation(dbLocation: BDLocation?) {
            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
            //以下只列举部分获取经纬度相关（常用）的结果信息
            //更多结果信息获取说明，请参照类参考中BDLocation类中的说明

            if (dbLocation == null) {
                return
            }
            //获取纬度信息
            val latitude = dbLocation.latitude.toFloat()
            //获取经度信息
            val longitude = dbLocation.longitude.toFloat()
            logD("经纬度：$latitude    $longitude")
            if (latitude <= 0 || longitude <= 0) {
                onLocationListener?.onFail()
                return
            }
            var address = dbLocation.address
            logD(
                "latitude:$latitude,longitude:$longitude,adCode:${dbLocation.adCode}," +
                        "addrStr:${dbLocation.addrStr},address:${address},city:${dbLocation.city} ${dbLocation.cityCode}" +
                        "${dbLocation.buildingName},${dbLocation.street},${dbLocation.locationDescribe},${dbLocation.locationWhere}"
            )
            var locate = if (!dbLocation.district.isNullOrBlank()) {
                dbLocation.district
            } else if (!dbLocation.city.isNullOrBlank()) {
                dbLocation.city
            } else if (!dbLocation.province.isNullOrBlank()) {
                dbLocation.province
            } else {
                ""
            }
            SP.putString("location", locate)
            SP.putString("city", dbLocation.city)
            SP.putFloat("latitude", dbLocation.latitude.toFloat())
            SP.putFloat("longitude", dbLocation.longitude.toFloat())

            onLocationListener?.onReceiveLocation(dbLocation)
            locationClient?.unRegisterLocationListener(this)
            locationClient?.stop()
        }

    }

    fun stop() {
        locationClient?.unRegisterLocationListener(listener)
        locationClient?.stop()
    }

    interface OnLocationListener {
        fun onReceiveLocation(location: BDLocation)
        fun onFail()
    }

    fun requestLoc(applicationContext: Context, onLocationListener: OnLocationListener?) {
        this.onLocationListener = onLocationListener
        if (locationClient != null && locationClient!!.isStarted) {
            locationClient?.unRegisterLocationListener(listener)
            locationClient?.stop()
        }
        //定位服务的客户端。宿主程序在客户端声明此类，并调用，目前只支持在主线程中启动
        locationClient = LocationClient(applicationContext)
        //声明LocationClient类实例并配置定位参数
        val locationOption = LocationClientOption()
        //注册监听函数
        locationClient?.registerLocationListener(listener)
        //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        locationOption.locationMode = LocationClientOption.LocationMode.Hight_Accuracy
        //可选，默认gcj02，设置返回的定位结果坐标系，如果配合百度地图使用，建议设置为bd09ll;
        locationOption.setCoorType("bd09ll")
//        locationOption.setCoorType("gcj02")
        //可选，默认0，即仅定位一次，设置发起连续定位请求的间隔需要大于等于1000ms才是有效的
        locationOption.setScanSpan(10 * 1000)
        //可选，设置是否需要地址信息，默认不需要
        locationOption.setIsNeedAddress(true)
        //可选，设置是否需要地址描述
        locationOption.setIsNeedLocationDescribe(true)
        //可选，设置是否需要设备方向结果
        locationOption.setNeedDeviceDirect(false)
        //可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        locationOption.isLocationNotify = false
        //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        locationOption.setIgnoreKillProcess(true)
        //可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        locationOption.setIsNeedLocationDescribe(true)
        //可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        locationOption.setIsNeedLocationPoiList(true)
        //可选，默认false，设置是否收集CRASH信息，默认收集
        locationOption.SetIgnoreCacheException(false)
        //可选，默认false，设置是否开启Gps定位
        locationOption.isOpenGps = true
        //可选，默认false，设置定位时是否需要海拔信息，默认不需要，除基础定位版本都可用
        locationOption.setIsNeedAltitude(false)
        //设置打开自动回调位置模式，该开关打开后，期间只要定位SDK检测到位置变化就会主动回调给开发者，该模式下开发者无需再关心定位间隔是多少，定位SDK本身发现位置变化就会及时回调给开发者
//        locationOption.setOpenAutoNotifyMode()
        //设置打开自动回调位置模式，该开关打开后，期间只要定位SDK检测到位置变化就会主动回调给开发者
//        locationOption.setOpenAutoNotifyMode(3000, 1, LocationClientOption.LOC_SENSITIVITY_HIGHT)
        //需将配置好的LocationClientOption对象，通过setLocOption方法传递给LocationClient对象使用
        locationClient?.locOption = locationOption
        //开始定位
        locationClient?.start()
    }
}