package com.huania.eew_bid.data.bean

import com.huania.eew_bid.ext.*


class Config {
    var company: String = ""
    var installLoc: String = ""
    var location: String = ""
    var lat: Float = 0f
    var lng: Float = 0f
    var voice: Int = 20
    var dayMagnitude: Float = 3.0f
    var dayIntensity: Float = 0.0f
    var nightMagnitude: Float = 3.0f
    var nightIntensity: Float = 0.0f
    var quakeSensor: Float = 1.0f
    var auxiDeliver: String = DefaultAuxiDeliver
    var mainDeliver: String = DefaultMainDeliver
    var manager: String = DefaultManager
    var managerFile: String = DefaultManagerFile
}

class DrillInfo{
    var type:Int=0
    var createdAt:Long=0
    var startAt:Long=0
    var updateAt:Long=0
    var depth:Int=0
    var epicenter:String=""
    var status:String=""
    var warnningLevel:String=""
    var msgType:String=""
    var longitude:Float=0f
    var latitude:Float=0f
    var epiIntensity:Float=0f
    var magnitude:Float=0f
    var intensity:Float=0f
    var warnTime:Int=0
}
