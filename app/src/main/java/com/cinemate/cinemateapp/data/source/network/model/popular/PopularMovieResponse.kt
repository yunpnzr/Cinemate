package com.cinemate.cinemateapp.data.source.network.model.popular


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class PopularMovieResponse(
    @SerializedName("page")
    var page: Int?,
    @SerializedName("results")
    var results: List<ResultPopularMovieResponse>?,
    @SerializedName("total_pages")
    var totalPages: Int?,
    @SerializedName("total_results")
    var totalResults: Int?
)