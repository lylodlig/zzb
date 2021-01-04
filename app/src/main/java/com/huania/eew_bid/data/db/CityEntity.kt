package com.huania.eew_bid.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "tb_city"//定义表名
)
class CityEntity {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    var code: String = ""
    @ColumnInfo(name = "parent_code")
    var parentCode: String = ""
    var name: String = ""

    constructor(code: String, parentCode: String, name: String) {
        this.code = code
        this.parentCode = parentCode
        this.name = name
    }
}