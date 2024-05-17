package com.cinemate.cinemateapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieDetail (
    var id: Int,
    var title: String,
    var date: String,
    var rating: Double,
    var desc: String,
    var image: String,
    var coverImage: String
): Parcelable