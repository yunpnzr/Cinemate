package com.cinemate.cinemateapp.data.datasource.favorite

import com.cinemate.cinemateapp.data.source.local.database.dao.AppDao
import com.cinemate.cinemateapp.data.source.local.database.entity.AppEntity
import kotlinx.coroutines.flow.Flow

class FavoriteDataSourceImpl(
    private val dao: AppDao
): FavoriteDataSource {
    override fun getAllFavorites(): Flow<List<AppEntity>> {
        return dao.getAllFavorites()
    }

    override suspend fun insertFavorite(favorite: AppEntity): Long {
        return dao.insertFavorite(favorite)
    }

    override suspend fun deleteFavorite(favorite: AppEntity): Int {
        return dao.deleteFavorite(favorite)
    }

    override fun deleteAll() {
        return dao.deleteAll()
    }
}