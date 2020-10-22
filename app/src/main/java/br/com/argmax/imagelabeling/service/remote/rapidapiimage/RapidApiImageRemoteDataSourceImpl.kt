package br.com.argmax.imagelabeling.service.remote.rapidapiimage

import br.com.argmax.imagelabeling.service.entities.rapidapientities.ImageResponseDto

class RapidApiImageRemoteDataSourceImpl(
    private val mRapidApiImageApiDataSource: RapidApiImageApiDataSource
) : RapidApiImageRemoteDataSource {

    override suspend fun rapidApiImageListBySearchTerm(searchTerm: String): List<ImageResponseDto> {
        return mRapidApiImageApiDataSource.rapidApiImagesSearch(searchTerm).imageResponseDto
    }

}