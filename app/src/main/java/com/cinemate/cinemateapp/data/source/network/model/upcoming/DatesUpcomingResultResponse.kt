package com.cinemate.cinemateapp.data.source.network.model.upcoming

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class DatesUpcomingResultResponse(
    @SerializedName("maximum")
    var maximum: String?,
    @SerializedName("minimum")
    var minimum: String?
)