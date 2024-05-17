package com.cinemate.cinemateapp.data.source.network.model.detail

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ProductionCountry(
    @SerializedName("iso_3166_1")
    var iso31661: String?,
    @SerializedName("name")
    var name: String?,
)
