package com.cinemate.cinemateapp.data.repository.favorite

import com.cinemate.cinemateapp.data.model.Favorite
import com.cinemate.cinemateapp.data.model.Movie
import com.cinemate.cinemateapp.data.model.MovieDetail
import com.cinemate.cinemateapp.utils.ResultWrapper
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {
    fun getAllFavorite(): Flow<ResultWrapper<Pair<List<Favorite>, Double>>>
    fun createFavorite(item: Movie): Flow<ResultWrapper<Boolean>>
    fun deleteFavorite(item: Favorite): Flow<ResultWrapper<Boolean>>
    fun deleteAll(): Flow<ResultWrapper<Boolean>>
}