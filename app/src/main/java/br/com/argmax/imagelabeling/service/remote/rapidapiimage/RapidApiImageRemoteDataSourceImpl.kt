package br.com.argmax.imagelabeling.service.remote.rapidapiimage

import br.com.argmax.imagelabeling.service.entities.rapidapientities.RapidApiImageResponseDto

class RapidApiImageRemoteDataSourceImpl(
    private val mRapidApiImageApiDataSource: RapidApiImageApiDataSource
) : RapidApiImageRemoteDataSource {

    override suspend fun rapidApiImageListBySearchTerm(
        searchTerm: String,
        offset: Int
    ): List<RapidApiImageResponseDto> {
        return mRapidApiImageApiDataSource.rapidApiImagesSearch(searchTerm, offset).imageResponseDto
    }

}