package com.cinemate.cinemateapp.data.datasource.favorite

import com.cinemate.cinemateapp.data.source.local.database.dao.AppDao
import com.cinemate.cinemateapp.data.source.local.database.entity.AppEntity
import kotlinx.coroutines.flow.Flow

class FavoriteDataSourceImpl(
    private val dao: AppDao,
) : FavoriteDataSource {
    override fun getAllFavorites(): Flow<List<AppEntity>> = dao.getAllFavorites()

    override fun checkFavoriteById(movieId: Int?): Flow<List<AppEntity>> = dao.checkFavoriteById(movieId)

    override suspend fun insertFavorite(favorite: AppEntity): Long = dao.insertFavorite(favorite)

    override suspend fun deleteFavorite(favorite: AppEntity): Int = dao.deleteFavorite(favorite)

    override suspend fun removeFavoriteById(favoriteId: Int?): Int = dao.removeFavorite(favoriteId)

    override suspend fun deleteAll() = dao.deleteAll()
}
