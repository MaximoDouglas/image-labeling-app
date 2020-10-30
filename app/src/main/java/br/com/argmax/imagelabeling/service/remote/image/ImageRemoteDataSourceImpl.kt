package br.com.argmax.imagelabeling.service.remote.image

import br.com.argmax.imagelabeling.service.entities.image.ImageRequestDto
import br.com.argmax.imagelabeling.service.entities.image.ImageResponseDto

class ImageRemoteDataSourceImpl(
    private val mImageClassApiDataSource: ImageApiDataSource
) : ImageRemoteDataSource {

    override suspend fun sendImage(imageRequestDto: ImageRequestDto): ImageResponseDto {
        return mImageClassApiDataSource.sendImage(imageRequestDto)
    }

}