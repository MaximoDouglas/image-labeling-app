package br.com.argmax.imagelabeling.service.entities.googleimage

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class SerpApiImageResponseDto(
    @SerializedName("contentUrl")
    val url: String
) : Serializable