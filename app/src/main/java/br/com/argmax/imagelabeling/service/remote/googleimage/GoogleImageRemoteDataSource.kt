package br.com.argmax.imagelabeling.service.remote.googleimage

import br.com.argmax.imagelabeling.service.entities.rapidapientities.ImageResponseDto

interface GoogleImageRemoteDataSource {

    suspend fun googleImageListBySearchTerm(searchTerm: String): List<ImageResponseDto>

}