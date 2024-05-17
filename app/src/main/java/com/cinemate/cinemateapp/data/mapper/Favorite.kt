package com.cinemate.cinemateapp.data.mapper

import com.cinemate.cinemateapp.data.model.Movie
import com.cinemate.cinemateapp.data.source.local.database.entity.AppEntity


fun Movie?.toAppEntity() =
    AppEntity(
        movieId = this?.id ?: 0,
        movieTitle = this?.title.orEmpty(),
        movieDate = this?.date.orEmpty(),
        movieRating = this?.rating ?: 0.0,
        movieDesc = this?.desc.orEmpty(),
        movieImage = this?.image.orEmpty(),
    )

fun AppEntity?.toFavorite() =
    Movie(
        id = this?.movieId ?: 0,
        title = this?.movieTitle.orEmpty(),
        date = this?.movieDate.orEmpty(),
        rating = this?.movieRating ?: 0.0,
        desc = this?.movieDesc.orEmpty(),
        image = this?.movieImage.orEmpty(),
    )

fun Collection<AppEntity?>.toFavoriteList() = this.map { it.toFavorite() }