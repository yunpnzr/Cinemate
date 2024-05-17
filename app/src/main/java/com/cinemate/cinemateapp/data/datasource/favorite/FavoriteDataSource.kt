package com.cinemate.cinemateapp.data.datasource.favorite

import androidx.room.Query
import com.cinemate.cinemateapp.data.source.local.database.dao.AppDao
import com.cinemate.cinemateapp.data.source.local.database.entity.AppEntity
import kotlinx.coroutines.flow.Flow


interface FavoriteDataSource {
    fun getAllFavorites(): Flow<List<AppEntity>>

    fun checkFavoriteById(movieId: Int?): Flow<List<AppEntity>>

    suspend fun insertFavorite(favorite: AppEntity): Long

    suspend fun deleteFavorite(favorite: AppEntity): Int
    suspend fun removeFavoriteById(favoriteId: Int?): Int

    suspend fun deleteAll()
}
