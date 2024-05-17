package com.cinemate.cinemateapp.data.datasource.detail

import com.cinemate.cinemateapp.data.source.network.model.detail.DetailMovieResponse
import com.cinemate.cinemateapp.data.source.network.service.AppService

class DetailDataSourceImpl(private val service: AppService) : DetailDataSource {
    override suspend fun detailMovies(movieId: Int): DetailMovieResponse {
        return service.detail(movieId)
    }
}
