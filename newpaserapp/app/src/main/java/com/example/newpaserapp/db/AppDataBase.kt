package com.example.newpaserapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.newpaserapp.db.dao.FavoriteDao
import com.example.newpaserapp.db.dao.HistoryDao
import com.example.newpaserapp.db.dao.ProgramDescriptorDao
import com.example.newpaserapp.db.entity.Favorite
import com.example.newpaserapp.db.entity.SearchHistory
import com.example.newpaserapp.db.entity.TransportStreamProgramDescriptor

/**
 * @author xxm
 * 2022/7/21
 **/
@Database(
    version = 1,
    entities = [Favorite::class, SearchHistory::class, TransportStreamProgramDescriptor::class]
)
abstract class AppDataBase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
    abstract fun historyDao(): HistoryDao
    abstract fun programDescriptorDao(): ProgramDescriptorDao

    companion object {
        @Volatile
        private var instance: AppDataBase? = null

        @Synchronized
        fun getDataBase(context: Context): AppDataBase {
            instance?.let {
                return it
            }
            return Room.databaseBuilder(
                context.applicationContext, AppDataBase::class.java, "app_database"
            )
                .build().apply {
                    instance = this
                }
        }
    }
}