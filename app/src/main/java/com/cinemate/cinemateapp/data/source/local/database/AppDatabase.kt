package com.cinemate.cinemateapp.data.source.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.cinemate.cinemateapp.data.source.local.database.dao.AppDao
import com.cinemate.cinemateapp.data.source.local.database.entity.AppEntity

@Database(
    entities = [AppEntity::class],
    version = 3,
    exportSchema = false,
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun appDao(): AppDao

    companion object {
        private const val DB_NAME = "cinemate.db"

        fun createInstance(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                DB_NAME,
            ).fallbackToDestructiveMigration().build()
        }
    }
}