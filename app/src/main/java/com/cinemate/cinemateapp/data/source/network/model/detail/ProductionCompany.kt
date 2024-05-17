package com.cinemate.cinemateapp.data.source.network.model.detail

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ProductionCompany(
    @SerializedName("id")
    var id: Int?,
    @SerializedName("logo_path")
    var logoPath: String?,
    @SerializedName("name")
    var name: String?,
    @SerializedName("origin_country")
    var originCountry: String?,
)
