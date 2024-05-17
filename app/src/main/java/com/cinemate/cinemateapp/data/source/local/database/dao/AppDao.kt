package com.cinemate.cinemateapp.data.source.local.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cinemate.cinemateapp.data.source.local.database.entity.AppEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {
    @Query("SELECT * FROM movies")
    fun getAllFavorites(): Flow<List<AppEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: AppEntity): Long

    @Query("SELECT * FROM MOVIES WHERE movie_id = :movieId ")
    fun checkFavoriteById(movieId: Int?): Flow<List<AppEntity>>

    @Delete
    suspend fun deleteFavorite(favorite: AppEntity): Int

    @Query("DELETE FROM movies WHERE movie_id = :movieId")
    suspend fun removeFavorite(movieId: Int?): Int

    @Query("DELETE FROM movies")
    fun deleteAll()
}
