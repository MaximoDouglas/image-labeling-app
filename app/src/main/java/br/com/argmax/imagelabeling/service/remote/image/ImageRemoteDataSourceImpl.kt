package br.com.argmax.imagelabeling.service.remote.image

import br.com.argmax.imagelabeling.service.entities.image.ImageRequestDto
import br.com.argmax.imagelabeling.service.entities.image.ImageResponseDto

class ImageRemoteDataSourceImpl(
    private val mImageApiDataSource: ImageApiDataSource
) : ImageRemoteDataSource {

    override suspend fun sendImage(imageRequestDto: ImageRequestDto): ImageResponseDto {
        return mImageApiDataSource.sendImage(imageRequestDto)
    }

}