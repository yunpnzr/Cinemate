package com.cinemate.cinemateapp.data.mapper

import com.cinemate.cinemateapp.data.model.Movie
import com.cinemate.cinemateapp.data.source.network.model.nowplaying.ResultNowPlayingResponse
import com.cinemate.cinemateapp.data.source.network.model.popular.ResultPopularMovieResponse
import com.cinemate.cinemateapp.data.source.network.model.toprating.ResultTopRatedResponse
import com.cinemate.cinemateapp.data.source.network.model.upcoming.ResultUpcomingMovieResponse

fun ResultNowPlayingResponse?.toMovieNowPlaying() =
    Movie(
        id = this?.id ?: 0,
        title = this?.title.orEmpty(),
        date = this?.releaseDate.orEmpty(),
        rating = this?.voteAverage ?: 0.0,
        desc = this?.overview.orEmpty(),
        image = this?.posterPath.orEmpty()
    )

fun ResultPopularMovieResponse?.toPopularMovie() =
    Movie(
        id = this?.id ?: 0,
        title = this?.title.orEmpty(),
        date = this?.releaseDate.orEmpty(),
        rating = this?.voteAverage ?: 0.0,
        desc = this?.overview.orEmpty(),
        image = this?.posterPath.orEmpty()
    )

fun ResultTopRatedResponse?.toTopRatedMovie() =
    Movie(
        id = this?.id ?: 0,
        title = this?.title.orEmpty(),
        date = this?.releaseDate.orEmpty(),
        rating = this?.voteAverage ?: 0.0,
        desc = this?.overview.orEmpty(),
        image = this?.posterPath.orEmpty()
    )

fun ResultUpcomingMovieResponse?.toUpcomingMovie() =
    Movie(
        id = this?.id ?: 0,
        title = this?.title.orEmpty(),
        date = this?.releaseDate.orEmpty(),
        rating = this?.voteAverage ?: 0.0,
        desc = this?.overview.orEmpty(),
        image = this?.posterPath.orEmpty()
    )

fun Collection<ResultNowPlayingResponse>?.toMovieNowPlayed(): List<Movie> = this?.map { it.toMovieNowPlaying() } ?: listOf()
fun Collection<ResultPopularMovieResponse>?.toMoviePopular(): List<Movie> = this?.map { it.toPopularMovie() } ?: listOf()
fun Collection<ResultTopRatedResponse>?.toMovieTopRated(): List<Movie> = this?.map { it.toTopRatedMovie() } ?: listOf()
fun Collection<ResultUpcomingMovieResponse>?.toMovieUpcoming(): List<Movie> = this?.map { it.toUpcomingMovie() } ?: listOf()