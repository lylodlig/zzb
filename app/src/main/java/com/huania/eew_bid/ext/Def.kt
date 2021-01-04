package com.huania.eew_bid.ext

import androidx.annotation.IntDef

const val ScienceVoiceQuake =
    "地震预警是地震正在发生时，利用电波比地震波快的原理，在破坏性地震波到达前，提前几秒到几十秒发出警报。收到预警，请不用惊慌，合理避险，确保安全。室外，远离建筑；室内高层，就近避险；室内低层，疏散至空旷地带。地震可预警，预警能救命"

const val SpeechAppID = "=5bbc1b4a"
const val ntpServer = "ntp4.aliyun.com"
const val SERIAL_PORT_DEVICES = "/dev/ttyS4"
const val BAUD_RATE = "115200"

const val Velocity_SERIAL_PORT_DEVICES = "/dev/ttyS2"
const val Velocity_BAUD_RATE = "9600"
//地图的缩放
const val MapZoom = 10f

// 延迟加载地图的时间
const val MapDelayTime = 200L

const val PlaySoundTailTime: Long = 18

const val TwinklePeriod = 100L
const val DefaultManagerPwd = "123456"
const val DefaultClientPwd = "123456"
const val DefaultManager = "101.200.58.212:21001"
const val DefaultMainDeliver = "101.200.58.212:11001"
const val DefaultAuxiDeliver = "101.200.58.212:11002"
//192.168.1.123:9001/v1/image/view?path=images/hehe.png
const val DefaultManagerFile = "http://192.168.1.123:9001/v1"


const val spUsername = "spUsername"
const val spPassword = "spPassword"
const val spConfig = "spConfig"
const val spTemp = "spTemp"
const val spHumidity = "spHumidity"
const val spPower = "spPower"
const val spManagerPwd = "spManagerPwd"
const val spClientPwd = "spClientPwd"
const val spAuxiDeliver = "auxiDeliver"
const val spLogUploadUrl = "spLogUploadUrl"


const val SHOW_RED = 1
const val SHOW_YELLOW = 2
const val SHOW_RECORD = 3

@IntDef(SHOW_RED, SHOW_YELLOW, SHOW_RECORD)
@Target(AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.PROPERTY, AnnotationTarget.TYPE)
@Retention(AnnotationRetention.SOURCE)
@MustBeDocumented
annotation class ShowLevel

const val LEVEL_RED = 1
const val LEVEL_ORANGE = 2
const val LEVEL_YELLOW = 3
const val LEVEL_BLUE = 4

@IntDef(LEVEL_RED, LEVEL_ORANGE, LEVEL_YELLOW, LEVEL_BLUE)
@Target(AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.PROPERTY, AnnotationTarget.TYPE)
@Retention(AnnotationRetention.SOURCE)
@MustBeDocumented
annotation class QuakeLevel