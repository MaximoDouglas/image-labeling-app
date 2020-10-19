package br.com.argmax.imagelabeling.service.remote.googleimage

import br.com.argmax.imagelabeling.service.entities.googleimage.GoogleImageResponseDto

interface GoogleImageRemoteDataSource {

    suspend fun googleImageListBySearchTerm(searchTerm: String): List<GoogleImageResponseDto>

}