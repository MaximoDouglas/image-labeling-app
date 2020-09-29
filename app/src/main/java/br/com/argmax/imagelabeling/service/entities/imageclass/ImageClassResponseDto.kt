package br.com.argmax.imagelabeling.service.entities.imageclass

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.Date

data class ImageClassResponseDto(
    val id: Int,
    val name: String,
    @SerializedName("domain_id")
    val domainId: Int,
    @SerializedName("created_at")
    val createdAt: Date,
    @SerializedName("updated_at")
    val updatedAt: Date
) : Serializable