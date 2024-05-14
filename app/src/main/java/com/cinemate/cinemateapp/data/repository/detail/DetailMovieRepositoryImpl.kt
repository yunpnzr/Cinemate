package com.cinemate.cinemateapp.data.repository.detail

import com.cinemate.cinemateapp.data.datasource.detail.DetailDataSource
import com.cinemate.cinemateapp.data.mapper.toDetail
import com.cinemate.cinemateapp.data.model.MovieDetail
import com.cinemate.cinemateapp.utils.ResultWrapper
import com.cinemate.cinemateapp.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

class DetailMovieRepositoryImpl(private val dataSource: DetailDataSource): DetailMovieRepository {
    override fun detailMovies(movieId: Int): Flow<ResultWrapper<MovieDetail>> {
        return proceedFlow {
            dataSource.detailMovies(movieId).toDetail()
        }
    }
}