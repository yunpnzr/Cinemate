package com.cinemate.cinemateapp.data.source.local.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class FavoriteEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    @ColumnInfo(name = "movie_id")
    var movieId: Int? = null,
    @ColumnInfo(name = "movie_title")
    var movieTitle: String,
    @ColumnInfo(name = "movie_image")
    var movieImage: String,
    @ColumnInfo(name = "movie_rating")
    var movieRating: Double,
    @ColumnInfo(name = "movie_date")
    var movieDate: String,
    @ColumnInfo(name = "movie_desc")
    var movieDesc: String,
    @ColumnInfo(name = "movie_bool")
    var movieBool: Boolean,
)
