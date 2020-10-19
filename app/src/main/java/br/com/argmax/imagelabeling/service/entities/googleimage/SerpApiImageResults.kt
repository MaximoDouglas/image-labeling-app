package br.com.argmax.imagelabeling.service.entities.googleimage

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class SerpApiImageResults (
    @SerializedName("images_results")
    val googleImageResponseDtoList: List<GoogleImageResponseDto>
) : Serializable