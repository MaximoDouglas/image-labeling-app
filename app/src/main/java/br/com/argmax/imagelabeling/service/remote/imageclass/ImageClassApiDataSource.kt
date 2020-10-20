package br.com.argmax.imagelabeling.service.remote.imageclass

import br.com.argmax.imagelabeling.BuildConfig
import br.com.argmax.imagelabeling.service.entities.imageclass.ImageClassRequestDto
import br.com.argmax.imagelabeling.service.entities.imageclass.ImageClassResponseDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ImageClassApiDataSource {

    @GET(BuildConfig.BASE_URL + "domains/{domain_id}/image_classes")
    suspend fun imageClassList(
        @Path(value = "domain_id") domainId: Int
    ): List<ImageClassResponseDto>

    @POST(BuildConfig.BASE_URL + "image_classes/")
    suspend fun createImageClass(
        @Body imageClassRequestDto: ImageClassRequestDto
    ): ImageClassResponseDto

}