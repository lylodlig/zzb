package com.huania.eew_bid.data.db

import android.annotation.SuppressLint
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.huania.eew_bid.app.App
import com.huania.eew_bid.utils.log.logD

/**
 * 数据库文件
 */
@Database(
    entities = [ProvinceEntity::class, CityEntity::class, CountyEntity::class, EarthquakeEntity::class, DrillEntity::class, SelfCheckEntity::class],
    version = 1,
    exportSchema = false
)
//@TypeConverters(Converters::class)
abstract class AppDataBase : RoomDatabase() {
    abstract fun provinceDao(): ProvinceDao
    abstract fun cityDao(): CityDao
    abstract fun countyDao(): CountyDao
    abstract fun earthquakeDao(): EarthquakeDao
    abstract fun drillDao(): DrillDao
    abstract fun checkDao(): SelfCheckDao

    companion object {
        @Volatile
        private var instance: AppDataBase? = null

        fun getInstance(): AppDataBase {
            return instance ?: synchronized(this) {
                instance ?: buildDataBase(App.instance)
                    .also {
                        instance = it
                    }
            }
        }

        fun getInstance(context: Context): AppDataBase {
            return instance ?: synchronized(this) {
                instance ?: buildDataBase(context)
                    .also {
                        instance = it
                    }
            }
        }

        private fun buildDataBase(context: Context): AppDataBase {
            return Room
                .databaseBuilder(context, AppDataBase::class.java, "database.db")
                .allowMainThreadQueries()
                .addCallback(object : RoomDatabase.Callback() {
                    @SuppressLint("CheckResult")
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        logD("创建数据库")
                    }
                }).build()
        }
    }


}