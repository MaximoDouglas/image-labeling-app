package br.com.argmax.imagelabeling.service.remote.imageclass

import br.com.argmax.imagelabeling.service.entities.imageclass.ImageClassResponseDto

class ImageClassRemoteDataSourceImpl(
    private val mImageClassApiDataSource: ImageClassApiDataSource
) : ImageClassRemoteDataSource {

    override suspend fun imageClassListByDomainId(domainId: Int): List<ImageClassResponseDto> {
        return mImageClassApiDataSource.imageClassList(domainId)
    }

}