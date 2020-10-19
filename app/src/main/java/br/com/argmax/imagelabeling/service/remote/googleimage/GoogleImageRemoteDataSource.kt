package br.com.argmax.imagelabeling.service.remote.googleimage

import br.com.argmax.imagelabeling.service.entities.imageclass.ImageClassRequestDto
import br.com.argmax.imagelabeling.service.entities.imageclass.ImageClassResponseDto

interface GoogleImageRemoteDataSource {

    suspend fun imageClassListByDomainId(domainId: Int): List<ImageClassResponseDto>
    
    suspend fun createImageClass(imageClassRequestDto: ImageClassRequestDto): ImageClassResponseDto

}