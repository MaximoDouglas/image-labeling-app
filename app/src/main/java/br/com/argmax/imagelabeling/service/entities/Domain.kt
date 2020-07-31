package br.com.argmax.imagelabeling.service.entities

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

data class Domain(
    val id: Int,
    val description: String,
    @SerializedName("created_at")
    val createdAt: Date,
    @SerializedName("updated_at")
    val updatedAt: Date
) : Serializable