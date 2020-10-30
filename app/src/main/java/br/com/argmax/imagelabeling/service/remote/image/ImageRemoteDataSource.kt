package br.com.argmax.imagelabeling.service.remote.image

import br.com.argmax.imagelabeling.service.entities.image.ImageRequestDto
import br.com.argmax.imagelabeling.service.entities.image.ImageResponseDto

interface ImageRemoteDataSource {

    suspend fun sendImage(imageRequestDto: ImageRequestDto): ImageResponseDto

}