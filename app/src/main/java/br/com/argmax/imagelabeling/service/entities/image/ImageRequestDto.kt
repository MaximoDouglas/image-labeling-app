package br.com.argmax.imagelabeling.service.entities.image

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ImageRequestDto(
    val url: String,
    @SerializedName("image_class_id")
    val imageClassId: Int
) : Serializable