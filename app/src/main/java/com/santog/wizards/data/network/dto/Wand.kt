package com.santog.wizards.data.network.dto


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Wand(
    @SerializedName("core")
    val core: String,
    @SerializedName("length")
    val length: Double,
    @SerializedName("wood")
    val wood: String
)
