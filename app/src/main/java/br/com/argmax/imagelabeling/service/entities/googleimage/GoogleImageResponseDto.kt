package br.com.argmax.imagelabeling.service.entities.googleimage

import java.io.Serializable

data class GoogleImageResponseDto(
    val id: Int,
    val url: String
) : Serializable