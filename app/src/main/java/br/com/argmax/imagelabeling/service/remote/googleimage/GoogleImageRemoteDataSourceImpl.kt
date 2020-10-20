package br.com.argmax.imagelabeling.service.remote.googleimage

import br.com.argmax.imagelabeling.service.entities.googleimage.GoogleImageResponseDto

class GoogleImageRemoteDataSourceImpl(
    private val mGoogleImageApiDataSource: GoogleImageApiDataSource
) : GoogleImageRemoteDataSource {

    override suspend fun googleImageListBySearchTerm(searchTerm: String): List<GoogleImageResponseDto> {
        return mGoogleImageApiDataSource.googleImageList(searchTerm).googleImageResponseDtoList
    }

}