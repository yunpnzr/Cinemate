package com.cinemate.cinemateapp.data.repository.detail

import com.cinemate.cinemateapp.data.model.MovieDetail
import com.cinemate.cinemateapp.utils.ResultWrapper
import kotlinx.coroutines.flow.Flow

interface DetailMovieRepository {
    fun detailMovies(movieId: Int): Flow<ResultWrapper<MovieDetail>>
}