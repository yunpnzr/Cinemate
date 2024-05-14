package com.cinemate.cinemateapp.data.datasource.detail

import com.cinemate.cinemateapp.data.source.network.model.detail.DetailMovieResponse

interface DetailDataSource {
    suspend fun detailMovies(movieId: Int): DetailMovieResponse
}