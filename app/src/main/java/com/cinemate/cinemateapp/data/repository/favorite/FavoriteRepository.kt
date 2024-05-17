package com.cinemate.cinemateapp.data.repository.favorite


import com.cinemate.cinemateapp.data.model.Movie
import com.cinemate.cinemateapp.data.source.local.database.entity.AppEntity
import com.cinemate.cinemateapp.utils.ResultWrapper
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {

    fun getAllFavorite(): Flow<ResultWrapper<Pair<List<Movie>, Double>>>
    fun checkFavoriteById(movieId: Int?): Flow<List<AppEntity>>
    fun createFavorite(item: Movie): Flow<ResultWrapper<Boolean>>
    fun deleteFavorite(item: Movie): Flow<ResultWrapper<Boolean>>
    fun removeFavoriteById(favoriteId: Int?): Flow<ResultWrapper<Boolean>>
    fun deleteAll(): Flow<ResultWrapper<Boolean>>
}