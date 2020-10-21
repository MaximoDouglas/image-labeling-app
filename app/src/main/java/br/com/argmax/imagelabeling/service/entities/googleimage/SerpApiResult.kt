package br.com.argmax.imagelabeling.service.entities.googleimage

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class SerpApiResult (
    @SerializedName("value")
    val serpApiImageResponseDtoList: List<SerpApiImageResponseDto>
) : Serializable