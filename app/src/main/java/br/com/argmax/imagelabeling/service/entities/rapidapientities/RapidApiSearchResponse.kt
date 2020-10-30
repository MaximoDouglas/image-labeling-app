package br.com.argmax.imagelabeling.service.entities.rapidapientities

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class RapidApiSearchResponse (
    @SerializedName("value")
    val imageResponseDto: List<RapidApiImageResponseDto>
) : Serializable

data class RapidApiImageResponseDto(
    @SerializedName("contentUrl")
    val url: String
) : Serializable