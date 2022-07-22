package com.example.newpaserapp.db.dao

import androidx.room.*
import com.example.newpaserapp.db.entity.SearchHistory

/**
 * @author xxm
 * 2022/7/21
 **/
@Dao
interface HistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(searchHistory: SearchHistory)

    @Delete
    fun delete(searchHistory: SearchHistory)

    @Query("delete  from History")
    fun deleteAll()

    @Query("select * from History")
    fun queryAll(): List<SearchHistory>
}