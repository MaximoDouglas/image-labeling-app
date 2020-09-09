package br.com.argmax.imagelabeling.service.entities

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ImageClassRequestDto(
    val id: Int,
    val name: String,
    @SerializedName("domain_id")
    val domainId: Int
) : Serializable