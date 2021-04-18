package br.com.argmax.imagelabeling.service.remote.imageclass

import br.com.argmax.imagelabeling.service.entities.imageclass.ImageClassRequestDto
import br.com.argmax.imagelabeling.service.entities.imageclass.ImageClassResponseDto
import retrofit2.Response
import retrofit2.http.*

interface ImageClassApiDataSource {

    @GET("domains/{domain_id}/image_classes")
    suspend fun imageClassList(
        @Path(value = "domain_id") domainId: Int
    ): List<ImageClassResponseDto>

    @POST("image_classes/")
    suspend fun createImageClass(
        @Body imageClassRequestDto: ImageClassRequestDto
    ): ImageClassResponseDto

    @PUT("image_classes/{image_class_id}")
    suspend fun editImageClassName(
        @Path(value = "image_class_id") imageClassId: Int,
        @Body imageClassRequestDto: ImageClassRequestDto
    ): ImageClassResponseDto

    @DELETE("image_classes/{image_class_id}")
    suspend fun deleteImageClass(
        @Path(value = "image_class_id") imageClassId: Int
    ): Response<Unit>

}