package br.com.argmax.imagelabeling.service.remote.rapidapiimage

import br.com.argmax.imagelabeling.service.entities.rapidapientities.ImageResponseDto

interface RapidApiImageRemoteDataSource {

    suspend fun rapidApiImageListBySearchTerm(searchTerm: String): List<ImageResponseDto>

}