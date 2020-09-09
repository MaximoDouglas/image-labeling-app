package br.com.argmax.imagelabeling.service.entities

import java.io.Serializable

data class ImageClassRequestDto(
    val name: String,
    val domain_id: String
) : Serializable