package com.cinemate.cinemateapp.data.datasource.favorite

import com.cinemate.cinemateapp.data.source.local.database.entity.AppEntity
import kotlinx.coroutines.flow.Flow

interface FavoriteDataSource {
    fun getAllFavorites(): Flow<List<AppEntity>>

    suspend fun insertFavorite(favorite: AppEntity): Long

    suspend fun deleteFavorite(favorite: AppEntity): Int

    fun deleteAll()
}