package com.huania.eew_bid.data.db

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "tb_county"//定义表名
)
class CountyEntity() : Parcelable {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    var code: String = ""
    @ColumnInfo(name = "parent_code")
    var parentCode: String = ""
    var name: String = ""
    var location: String = ""
    var lat: Float = 0f
    var lng: Float = 0f

    constructor(parcel: Parcel) : this() {
        id = parcel.readLong()
        code = parcel.readString()!!
        parentCode = parcel.readString()!!
        name = parcel.readString()!!
        location = parcel.readString()!!
        lat = parcel.readFloat()
        lng = parcel.readFloat()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(code)
        parcel.writeString(parentCode)
        parcel.writeString(name)
        parcel.writeString(location)
        parcel.writeFloat(lat)
        parcel.writeFloat(lng)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        return "CountyEntity(id=$id, code='$code', parentCode='$parentCode', name='$name', lat=$lat, lng=$lng, location=$location)"
    }

    companion object CREATOR : Parcelable.Creator<CountyEntity> {
        override fun createFromParcel(parcel: Parcel): CountyEntity {
            return CountyEntity(parcel)
        }

        override fun newArray(size: Int): Array<CountyEntity?> {
            return arrayOfNulls(size)
        }
    }


}