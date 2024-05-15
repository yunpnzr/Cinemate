package com.cinemate.cinemateapp.data.mapper

import com.cinemate.cinemateapp.data.model.Favorite
import com.cinemate.cinemateapp.data.source.local.database.entity.AppEntity

fun Favorite?.toAppEntity() =
    AppEntity(
        movieId = this?.movieId ?: 0,
        movieTitle = this?.movieTitle.orEmpty(),
        movieDate = this?.movieDate.orEmpty(),
        movieRating = this?.movieRating ?: 0.0,
        movieDesc = this?.movieDesc.orEmpty(),
        movieImage = this?.movieImage.orEmpty(),
        movieBool = this?.movieBool ?: false
    )

fun AppEntity?.toFavorite() =
    Favorite(
        movieId = this?.movieId ?: 0,
        movieTitle = this?.movieTitle.orEmpty(),
        movieDate = this?.movieDate.orEmpty(),
        movieRating = this?.movieRating ?: 0.0,
        movieDesc = this?.movieDesc.orEmpty(),
        movieImage = this?.movieImage.orEmpty(),
        movieBool = this?.movieBool ?: false
    )

fun Collection<AppEntity?>.toFavoriteList() = this.map { it.toFavorite() }