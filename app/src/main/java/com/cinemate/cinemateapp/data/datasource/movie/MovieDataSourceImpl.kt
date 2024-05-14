package com.cinemate.cinemateapp.data.datasource.movie

import com.cinemate.cinemateapp.data.source.network.model.nowplaying.NowPlayingResponse
import com.cinemate.cinemateapp.data.source.network.model.popular.PopularMovieResponse
import com.cinemate.cinemateapp.data.source.network.model.toprating.TopRatedResponse
import com.cinemate.cinemateapp.data.source.network.model.upcoming.UpcomingMovieResponse
import com.cinemate.cinemateapp.data.source.network.service.AppService

class MovieDataSourceImpl(private val service: AppService): MovieDataSource {
    override suspend fun getNowPlaying(page: Int): NowPlayingResponse {
        return service.nowPlaying(page)
    }

    override suspend fun getPopular(page: Int): PopularMovieResponse {
        return service.popular(page)
    }

    override suspend fun getTopRating(page: Int): TopRatedResponse {
        return service.topRated(page)
    }

    override suspend fun getUpcoming(page: Int): UpcomingMovieResponse {
        return service.upcoming(page)
    }
}