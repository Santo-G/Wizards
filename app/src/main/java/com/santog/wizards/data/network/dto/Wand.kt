package com.santog.wizards.data.network.dto


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Wand(
    @SerializedName("core")
    val core: String,
    @SerializedName("length")
    val length: Double,
    @SerializedName("wood")
    val wood: String
)
