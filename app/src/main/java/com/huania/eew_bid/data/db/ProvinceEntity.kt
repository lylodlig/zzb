package com.huania.eew_bid.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "tb_province"//定义表名
)
class ProvinceEntity {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    var code: String = ""
    var name: String = ""

    constructor(code: String, name: String) {
        this.code = code
        this.name = name
    }
}