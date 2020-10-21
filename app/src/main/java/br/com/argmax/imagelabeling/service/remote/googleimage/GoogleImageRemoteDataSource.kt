package br.com.argmax.imagelabeling.service.remote.googleimage

import br.com.argmax.imagelabeling.service.entities.googleimage.SerpApiImageResponseDto

interface GoogleImageRemoteDataSource {

    suspend fun googleImageListBySearchTerm(searchTerm: String): List<SerpApiImageResponseDto>

}