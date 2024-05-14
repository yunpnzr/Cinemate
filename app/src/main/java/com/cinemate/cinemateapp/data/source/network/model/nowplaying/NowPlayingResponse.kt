package com.cinemate.cinemateapp.data.source.network.model.nowplaying


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class NowPlayingResponse(
    @SerializedName("dates")
    var dates: DatesNowPlayingResponse?,
    @SerializedName("page")
    var page: Int?,
    @SerializedName("results")
    var results: List<ResultNowPlayingResponse>?,
    @SerializedName("total_pages")
    var totalPages: Int?,
    @SerializedName("total_results")
    var totalResults: Int?
)