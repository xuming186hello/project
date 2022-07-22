package com.example.newpaserapp.db.dao

import androidx.room.*
import com.example.newpaserapp.db.entity.Favorite

/**
 * @author xxm
 * 2022/7/21
 **/
@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(favorite: Favorite)

    @Delete
    fun delete(favorite: Favorite)

    @Query("select programNumber from Favorite where fileName == :fileName ")
    fun queryAll(fileName: String): List<Int>
}