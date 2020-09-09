package br.com.argmax.imagelabeling.service.entities.imageclass

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ImageClassRequestDto(
    val name: String,
    @SerializedName("domain_id")
    val domainId: Int
) : Serializable