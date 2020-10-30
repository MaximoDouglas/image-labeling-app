package br.com.argmax.imagelabeling.service.entities.image

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

data class ImageResponseDto(
    val id: Int,
    val url: String,
    @SerializedName("image_class_id")
    val imageClassId: Int,
    @SerializedName("created_at")
    val createdAt: Date,
    @SerializedName("updated_at")
    val updatedAt: Date
) : Serializable