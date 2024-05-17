package com.cinemate.cinemateapp.data.repository.seemore

import androidx.paging.PagingData
import com.cinemate.cinemateapp.data.model.Movie
import kotlinx.coroutines.flow.Flow

interface SeeMoreRepository {
    fun getAllPopularMovie(): Flow<PagingData<Movie>>

    fun getAllPlayNowMovie(): Flow<PagingData<Movie>>

    fun getAllTopRatedMovie(): Flow<PagingData<Movie>>

    fun getAllUpcomingMovie(): Flow<PagingData<Movie>>
}
