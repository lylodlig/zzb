package com.huania.eew_bid.data.db

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface EarthquakeDao {
    /**
     * 获取我的预警记录
     */
    @Query("SELECT  *  FROM tb_earthquake GROUP BY(event_id) ORDER BY real_time DESC")
    fun getMyEarthquake(): List<EarthquakeEntity>

    /**
     * 获取第一条数据
     */
    @Query("SELECT  *  FROM tb_earthquake GROUP BY(event_id) ORDER BY real_time DESC LIMIT 1")
    fun getFirstQuake(): List<EarthquakeEntity>

    @Query("SELECT * FROM tb_earthquake WHERE event_id=:id ORDER BY cur_countdown DESC")
    fun getFirstUpdateById(id: Long): List<EarthquakeEntity>

    @Query("SELECT * FROM tb_earthquake WHERE event_id=:id")
    fun getQuakeById(id: Long): List<EarthquakeEntity>

    @Query("SELECT * FROM tb_earthquake WHERE event_id=:id ORDER BY real_time ASC")
    fun getAllById(id: Long): List<EarthquakeEntity>

    @Insert
    fun insert(studentEntity: EarthquakeEntity)

    /**
     * 获取我的预警记录
     */
//    @Query("SELECT  *  FROM tb_earthquake  GROUP BY(event_id) ORDER BY real_time DESC")
//    fun getList(): DataSource.Factory<Int, EarthquakeEntity>
    @Query("SELECT  *  FROM tb_earthquake  GROUP BY(event_id) ORDER BY real_time DESC")
    fun getAll(): List<EarthquakeEntity>

    @Query("SELECT  *  FROM tb_earthquake WHERE (magnitude >=:mStart AND magnitude <=:mEnd) AND (start_at>=:tStart or :tStart='') AND (start_at<=:tEnd or :tEnd='') GROUP BY(event_id) ORDER BY real_time DESC")
    fun getList(
        mStart: Float,
        mEnd: Float,
        tStart: Long,
        tEnd: Long
    ): DataSource.Factory<Int, EarthquakeEntity>
}

@Dao
interface SelfCheckDao {
    @Insert
    fun insert(selfCheck: SelfCheckEntity)

    @Query("SELECT  *  FROM tab_self_check  ORDER BY date DESC")
    fun getList(): DataSource.Factory<Int, SelfCheckEntity>

    @Query("SELECT  *  FROM tab_self_check  ORDER BY date DESC")
    fun getAll(): List<SelfCheckEntity>

}

@Dao
interface DrillDao {

    @Query("SELECT  *  FROM tb_drill GROUP BY(event_id)  ORDER BY drill_time DESC")
    fun getAll(): DataSource.Factory<Int, DrillEntity>

    @Insert
    fun insert(entity: DrillEntity)
}

@Dao
interface CityDao {

    @Query("SELECT  *  FROM tb_city WHERE parent_code=:parentCode")
    fun getAll(parentCode: String): List<CityEntity>

    @Insert
    fun insert(entity: CityEntity)

    @Query("SELECT  *  FROM tb_city WHERE code=:code")
    fun get(code: String): List<CityEntity>
}

@Dao
interface CountyDao {

    @Query("SELECT  *  FROM tb_county WHERE parent_code=:parentCode")
    fun getAll(parentCode: String): List<CountyEntity>

    @Insert
    fun insert(entity: CountyEntity)

    @Query("SELECT  *  FROM tb_county WHERE code=:code")
    fun get(code: String): List<CountyEntity>
}


@Dao
interface ProvinceDao {

    @Query("SELECT  *  FROM tb_province")
    fun getAll(): List<ProvinceEntity>

    @Insert
    fun insert(entity: ProvinceEntity)

    @Query("SELECT  *  FROM tb_province WHERE code=:code")
    fun get(code: String): List<ProvinceEntity>
}
