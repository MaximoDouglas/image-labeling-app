package br.com.argmax.imagelabeling.service.remote.imageclass

import br.com.argmax.imagelabeling.service.entities.imageclass.ImageClassRequestDto
import br.com.argmax.imagelabeling.service.entities.imageclass.ImageClassResponseDto

class ImageClassRemoteDataSourceImpl(
    private val mImageClassApiDataSource: ImageClassApiDataSource
) : ImageClassRemoteDataSource {

    override suspend fun imageClassListByDomainId(domainId: Int): List<ImageClassResponseDto> {
        return mImageClassApiDataSource.imageClassList(domainId)
    }

    override suspend fun createImageClass(imageClassRequestDto: ImageClassRequestDto): ImageClassResponseDto {
        return mImageClassApiDataSource.createImageClass(imageClassRequestDto)
    }

    override suspend fun editImageClassName(
        imageClassId: Int,
        imageClassRequestDto: ImageClassRequestDto
    ): ImageClassResponseDto {
        return mImageClassApiDataSource.editImageClassName(
            imageClassId = imageClassId,
            imageClassRequestDto = imageClassRequestDto
        )
    }

    override suspend fun deleteImageClass(imageClassId: Int) {
        mImageClassApiDataSource.deleteImageClass(
            imageClassId = imageClassId
        )
    }

}