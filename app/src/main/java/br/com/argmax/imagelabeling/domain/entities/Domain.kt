package br.com.argmax.imagelabeling.domain.entities

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

class Domain(
    val id:          Int?,
    val description: String?,
    @SerializedName("created_at")
    val createdAt:   Date?,
    @SerializedName("updated_at")
    val updatedAt:   Date?
) : Serializable