package com.cinemate.cinemateapp.data.model

data class Favorite(
    var movieId: Int,
    var movieTitle: String,
    var movieDate: String,
    var movieRating: Double,
    var movieDesc: String,
    var movieImage: String,
    var movieBool: Boolean
)
