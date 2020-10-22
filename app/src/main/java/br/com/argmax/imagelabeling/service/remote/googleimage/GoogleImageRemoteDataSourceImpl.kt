package br.com.argmax.imagelabeling.service.remote.googleimage

import br.com.argmax.imagelabeling.service.entities.rapidapientities.ImageResponseDto

class GoogleImageRemoteDataSourceImpl(
    private val mGoogleImageApiDataSource: GoogleImageApiDataSource
) : GoogleImageRemoteDataSource {

    override suspend fun googleImageListBySearchTerm(searchTerm: String): List<ImageResponseDto> {
        return mGoogleImageApiDataSource.googleImageList(searchTerm).imageResponseDto
    }

}