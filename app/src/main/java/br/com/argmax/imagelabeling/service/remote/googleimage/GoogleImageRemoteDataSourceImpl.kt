package br.com.argmax.imagelabeling.service.remote.googleimage

import br.com.argmax.imagelabeling.service.entities.imageclass.ImageClassRequestDto
import br.com.argmax.imagelabeling.service.entities.imageclass.ImageClassResponseDto

class GoogleImageRemoteDataSourceImpl(
    private val mImageClassApiDataSource: ImageClassApiDataSource
) : ImageClassRemoteDataSource {

    override suspend fun imageClassListByDomainId(domainId: Int): List<ImageClassResponseDto> {
        return mImageClassApiDataSource.imageClassList(domainId)
    }

    override suspend fun createImageClass(imageClassRequestDto: ImageClassRequestDto): ImageClassResponseDto {
        return mImageClassApiDataSource.createImageClass(imageClassRequestDto)
    }

}