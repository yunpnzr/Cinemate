package com.cinemate.cinemateapp.data.datasource.favorite

import com.cinemate.cinemateapp.data.source.local.database.dao.AppDao
import com.cinemate.cinemateapp.data.source.local.database.entity.AppEntity
import kotlinx.coroutines.flow.Flow

class FavoriteDataSourceImpl(
    private val dao: AppDao
): FavoriteDataSource {

    override fun getAllFavorites(): Flow<List<AppEntity>> = dao.getAllFavorites()

    override suspend fun insertFavorite(favorite: AppEntity): Long = dao.insertFavorite(favorite)

    override suspend fun deleteFavorite(favorite: AppEntity): Int = dao.deleteFavorite(favorite)

    override suspend fun deleteAll() = dao.deleteAll()


}