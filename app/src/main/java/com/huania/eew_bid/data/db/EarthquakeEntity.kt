package com.huania.eew_bid.data.db

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.blankj.utilcode.util.TimeUtils
import com.huania.eew_bid.ext.ShowLevel
import com.huania.eew_bid.ext.round
import java.text.SimpleDateFormat
import java.util.*

@Entity(tableName = "tb_earthquake")
class EarthquakeEntity() : Parcelable {
    @PrimaryKey(autoGenerate = true) //定义主键
    var id: Long = 0

    @ColumnInfo(name = "event_id")
    var eventId: Long = 0//地震id
    var updates: Int = 0//地震报数
    var longitude: Float = 0f//经度
    var latitude: Float = 0f//维度
    var epicenter: String = ""//震中位置
    var depth: Int = 0

    @ColumnInfo(name = "start_at")
    var startAt: Long = 0

    @ColumnInfo(name = "update_at")
    var updateAt: Long = 0
    var magnitude: Float = 0f//震级

    @ColumnInfo(name = "real_time")
    var realTime: Long = 0//真实收到地震的时刻

    @ColumnInfo(name = "cur_intensity")
    var curIntensity: Float = 0f//烈度

    @ColumnInfo(name = "cur_countdown")
    var curCountdown: Int = 0//倒计时

    @ColumnInfo(name = "cur_location")
    var curLocation: String = ""//当时的地址

    @ColumnInfo(name = "cur_distance")
    var curDistance: Float = 0f//当时的距离

    @ColumnInfo(name = "cur_longitude")
    var curLongitude: Float = 0f//经度

    @ColumnInfo(name = "cur_latitude")
    var curLatitude: Float = 0f//维度

    @ColumnInfo(name = "cur_strategy")
    @ShowLevel
    var curStrategy: Int = 0

    @ColumnInfo(name = "calculate_countdown")
    var calculateCountdown: Int? = 0

    @ColumnInfo(name = "threshold_magnitude")
    var thresholdMagnitude: Float = 0f//震级阈值

    @ColumnInfo(name = "threshold_intensity")
    var thresholdIntensity: Float = 0f//烈度阈值

    @Ignore
    var isDrill: Boolean = false

    @Ignore
    var isFirstTrigger: Boolean = false

    constructor(parcel: Parcel) : this() {
        id = parcel.readLong()
        eventId = parcel.readLong()
        updates = parcel.readInt()
        longitude = parcel.readFloat()
        latitude = parcel.readFloat()
        epicenter = parcel.readString()!!
        depth = parcel.readInt()
        startAt = parcel.readLong()
        updateAt = parcel.readLong()
        magnitude = parcel.readFloat()
        realTime = parcel.readLong()
        curIntensity = parcel.readFloat()
        curCountdown = parcel.readInt()
        curLocation = parcel.readString()!!
        curDistance = parcel.readFloat()
        curLongitude = parcel.readFloat()
        curLatitude = parcel.readFloat()
        curStrategy = parcel.readInt()
        calculateCountdown = parcel.readValue(Int::class.java.classLoader) as? Int
        thresholdMagnitude = parcel.readFloat()
        thresholdIntensity = parcel.readFloat()
        isDrill = parcel.readByte() != 0.toByte()
        isFirstTrigger = parcel.readByte() != 0.toByte()
    }

    fun updateAtStr(): String {
        if (updateAt <= 0) return ""
        return TimeUtils.date2String(Date(updateAt))
    }

    fun getCountdown() = if (curCountdown < 0) 0 else curCountdown

    fun startAtStr(): String {
        if (startAt <= 0) return ""
        return TimeUtils.date2String(Date(startAt), SimpleDateFormat("yyyy-MM-dd HH:mm:ss"))
    }


    fun getMagnitude(): String {
        return magnitude.round(1)
    }

    fun getLat(): String {
        return latitude.round(2)
    }

    fun getLng(): String {
        return longitude.round(2)
    }

    fun getDistance(): String {
        return curDistance.round(0)
    }

    fun getIntensity(): String {
        return curIntensity.round(1)
    }

    fun getShowCountdown(): Int {
        return if (curCountdown < 0) 0
        else curCountdown.toFloat().round(0).toInt()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeLong(eventId)
        parcel.writeInt(updates)
        parcel.writeFloat(longitude)
        parcel.writeFloat(latitude)
        parcel.writeString(epicenter)
        parcel.writeInt(depth)
        parcel.writeLong(startAt)
        parcel.writeLong(updateAt)
        parcel.writeFloat(magnitude)
        parcel.writeLong(realTime)
        parcel.writeFloat(curIntensity)
        parcel.writeInt(curCountdown)
        parcel.writeString(curLocation)
        parcel.writeFloat(curDistance)
        parcel.writeFloat(curLongitude)
        parcel.writeFloat(curLatitude)
        parcel.writeInt(curStrategy)
        parcel.writeValue(calculateCountdown)
        parcel.writeFloat(thresholdMagnitude)
        parcel.writeFloat(thresholdIntensity)
        parcel.writeByte(if (isDrill) 1 else 0)
        parcel.writeByte(if (isFirstTrigger) 1 else 0)
    }

    override fun equals(other: Any?): Boolean {
        if ((other as EarthquakeEntity).eventId == eventId) {
            return true
        }
        return super.equals(other)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        return "EarthquakeEntity(id=$id, eventId=$eventId, updates=$updates, longitude=$longitude, latitude=$latitude, epicenter='$epicenter', depth=$depth, startAt=$startAt, updateAt=$updateAt, magnitude=$magnitude, realTime=$realTime, curIntensity=$curIntensity, curCountdown=$curCountdown, curLocation='$curLocation', curDistance=$curDistance, curLongitude=$curLongitude, curLatitude=$curLatitude, curStrategy=$curStrategy, calculateCountdown=$calculateCountdown, thresholdMagnitude=$thresholdMagnitude, thresholdIntensity=$thresholdIntensity, isDrill=$isDrill, isFirstTrigger=$isFirstTrigger)"
    }

    companion object CREATOR : Parcelable.Creator<EarthquakeEntity> {
        override fun createFromParcel(parcel: Parcel): EarthquakeEntity {
            return EarthquakeEntity(parcel)
        }

        override fun newArray(size: Int): Array<EarthquakeEntity?> {
            return arrayOfNulls(size)
        }
    }


}