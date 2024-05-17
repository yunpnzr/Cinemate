package com.cinemate.cinemateapp.data.source.local.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class AppEntity(
    @PrimaryKey
    @ColumnInfo("movie_id")
    var movieId: Int,
    @ColumnInfo("movie_title")
    var movieTitle: String,
    @ColumnInfo("movie_date")
    var movieDate: String,
    @ColumnInfo("movie_rating")
    var movieRating: Double,
    @ColumnInfo("movie_desc")
    var movieDesc: String,
    @ColumnInfo("movie_image")
    var movieImage: String
)