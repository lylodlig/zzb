package com.huania.eew_bid.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

/**
 *   created by lzy
 *   on 2020/2/24
 */
@Entity(tableName = "tab_self_check")
class SelfCheckEntity : Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0
    var wire: Boolean = false
    var wifi: Boolean = false
    var card: Boolean = false
    var server1: Boolean = false
    var server2: Boolean = false
    var server3: Boolean = false
    var power: Boolean = false
    var output: Boolean = false
    var battery: Boolean = false
    var voice: Boolean = false
    var light: Boolean = false
    var video: Boolean = false
    var quake: Boolean = false
    var date: Long = 0L
    var temp: String = ""
    var humidity: String = ""
}