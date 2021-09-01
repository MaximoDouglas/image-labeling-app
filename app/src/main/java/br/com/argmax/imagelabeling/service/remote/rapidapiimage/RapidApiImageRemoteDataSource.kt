package br.com.argmax.imagelabeling.service.remote.rapidapiimage

import br.com.argmax.imagelabeling.service.entities.rapidapientities.RapidApiImageResponseDto

interface RapidApiImageRemoteDataSource {

    suspend fun rapidApiImageListBySearchTerm(
        searchTerm: String,
        offset: Int = 0
    ): List<RapidApiImageResponseDto>

}