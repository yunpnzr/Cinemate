package com.cinemate.cinemateapp.data.datasource.movie

import com.cinemate.cinemateapp.data.source.network.model.nowplaying.NowPlayingResponse
import com.cinemate.cinemateapp.data.source.network.model.popular.PopularMovieResponse
import com.cinemate.cinemateapp.data.source.network.model.toprating.TopRatedResponse
import com.cinemate.cinemateapp.data.source.network.model.upcoming.UpcomingMovieResponse

interface MovieDataSource {
    suspend fun getNowPlaying(page: Int): NowPlayingResponse
    suspend fun getPopular(page: Int): PopularMovieResponse
    suspend fun getTopRating(page: Int): TopRatedResponse
    suspend fun getUpcoming(page: Int): UpcomingMovieResponse
}