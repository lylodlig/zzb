package com.huania.eew_bid.data.db

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.huania.eew_bid.ext.round
import java.text.SimpleDateFormat
import java.util.*

@Entity(tableName = "tb_drill")//定义表名
class DrillEntity {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    var type: Int = 0//0本机 1远程
    var drill_time: Long = 0
    var event_id: Long = 0
    var epicenter: String = ""
    var magnitude = 0f
    var countdown = 0
    var intensity = 0f

    @Ignore
    var serial = ""

    fun getTypeStr() = if (type == 0) "本机" else "远程"
    fun getDrillTimeStr() = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date(drill_time))
    fun getIntensityStr()=intensity.round(1).toString()
}