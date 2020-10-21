package br.com.argmax.imagelabeling.service.remote.googleimage

import br.com.argmax.imagelabeling.service.entities.googleimage.SerpApiImageResponseDto

class GoogleImageRemoteDataSourceImpl(
    private val mGoogleImageApiDataSource: GoogleImageApiDataSource
) : GoogleImageRemoteDataSource {

    override suspend fun googleImageListBySearchTerm(searchTerm: String): List<SerpApiImageResponseDto> {
        return mGoogleImageApiDataSource.googleImageList(searchTerm).serpApiImageResponseDtoList
    }

}