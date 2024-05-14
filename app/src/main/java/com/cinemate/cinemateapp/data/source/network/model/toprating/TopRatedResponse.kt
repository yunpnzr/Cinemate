package com.cinemate.cinemateapp.data.source.network.model.toprating


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class TopRatedResponse(
    @SerializedName("page")
    var page: Int?,
    @SerializedName("results")
    var results: List<ResultTopRatedResponse>?,
    @SerializedName("total_pages")
    var totalPages: Int?,
    @SerializedName("total_results")
    var totalResults: Int?
)