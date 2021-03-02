package br.com.argmax.imagelabeling.service.remote.imageclass

import br.com.argmax.imagelabeling.service.entities.imageclass.ImageClassRequestDto
import br.com.argmax.imagelabeling.service.entities.imageclass.ImageClassResponseDto

interface ImageClassRemoteDataSource {

    suspend fun imageClassListByDomainId(domainId: Int): List<ImageClassResponseDto>
    
    suspend fun createImageClass(imageClassRequestDto: ImageClassRequestDto): ImageClassResponseDto

    suspend fun editImageClassName(imageClassId: Int, imageClassRequestDto: ImageClassRequestDto): ImageClassResponseDto

}