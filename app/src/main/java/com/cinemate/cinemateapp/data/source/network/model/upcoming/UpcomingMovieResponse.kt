package com.cinemate.cinemateapp.data.source.network.model.upcoming

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class UpcomingMovieResponse(
    @SerializedName("dates")
    var dates: DatesUpcomingResultResponse?,
    @SerializedName("page")
    var page: Int?,
    @SerializedName("results")
    var results: List<ResultUpcomingMovieResponse>?,
    @SerializedName("total_pages")
    var totalPages: Int?,
    @SerializedName("total_results")
    var totalResults: Int?
)