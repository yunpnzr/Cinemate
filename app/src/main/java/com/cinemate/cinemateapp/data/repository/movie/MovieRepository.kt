package com.cinemate.cinemateapp.data.repository.movie

import com.cinemate.cinemateapp.data.model.Movie
import com.cinemate.cinemateapp.utils.ResultWrapper
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getNowPlaying(page: Int): Flow<ResultWrapper<List<Movie>>>

    fun getPopular(page: Int): Flow<ResultWrapper<List<Movie>>>

    fun getTopRating(page: Int): Flow<ResultWrapper<List<Movie>>>

    fun getUpcoming(page: Int): Flow<ResultWrapper<List<Movie>>>
}
