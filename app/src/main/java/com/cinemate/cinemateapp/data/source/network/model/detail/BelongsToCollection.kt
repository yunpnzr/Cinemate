package com.cinemate.cinemateapp.data.source.network.model.detail

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class BelongsToCollection(
    @SerializedName("backdrop_path")
    var backdropPath: String?,
    @SerializedName("id")
    var id: Int?,
    @SerializedName("name")
    var name: String?,
    @SerializedName("poster_path")
    var posterPath: String?,
)
