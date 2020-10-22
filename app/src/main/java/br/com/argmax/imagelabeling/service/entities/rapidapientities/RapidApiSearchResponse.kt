package br.com.argmax.imagelabeling.service.entities.rapidapientities

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class RapidApiSearchResponse (
    @SerializedName("value")
    val imageResponseDto: List<ImageResponseDto>
) : Serializable

data class ImageResponseDto(
    @SerializedName("contentUrl")
    val url: String
) : Serializable