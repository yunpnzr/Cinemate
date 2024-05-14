package com.cinemate.cinemateapp.data.mapper

import com.cinemate.cinemateapp.data.model.MovieDetail
import com.cinemate.cinemateapp.data.source.network.model.detail.DetailMovieResponse

fun DetailMovieResponse?.toDetail() =
    MovieDetail(
        id = this?.id ?: 0,
        title = this?.title.orEmpty(),
        date = this?.releaseDate.orEmpty(),
        rating = this?.voteAverage ?: 0.0,
        desc = this?.overview.orEmpty(),
        image = this?.posterPath.orEmpty()
    )

fun Collection<DetailMovieResponse>?.toDetailMovie(): List<MovieDetail> = this?.map { it.toDetail() } ?: listOf()