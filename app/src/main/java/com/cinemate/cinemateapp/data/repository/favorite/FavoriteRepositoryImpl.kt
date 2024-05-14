package com.cinemate.cinemateapp.data.repository.favorite

import com.cinemate.cinemateapp.data.datasource.favorite.FavoriteDataSource
import com.cinemate.cinemateapp.data.model.Favorite
import com.cinemate.cinemateapp.data.model.MovieDetail
import com.cinemate.cinemateapp.utils.ResultWrapper
import kotlinx.coroutines.flow.Flow

class FavoriteRepositoryImpl(private val dataSource: FavoriteDataSource): FavoriteRepository {
    override fun getAllFavorite(): Flow<ResultWrapper<Pair<List<Favorite>, Double>>> {
        TODO("Not yet implemented")
    }

    override fun createFavorite(item: MovieDetail): Flow<ResultWrapper<Boolean>> {
        TODO("Not yet implemented")
    }

    override fun deleteFavorite(item: Favorite): Flow<ResultWrapper<Boolean>> {
        TODO("Not yet implemented")
    }

    override fun deleteAll(): Flow<ResultWrapper<Boolean>> {
        TODO("Not yet implemented")
    }
}