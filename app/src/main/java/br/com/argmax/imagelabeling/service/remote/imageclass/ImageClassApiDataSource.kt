package br.com.argmax.imagelabeling.service.remote.imageclass

import br.com.argmax.imagelabeling.service.entities.imageclass.ImageClassResponseDto
import retrofit2.http.GET
import retrofit2.http.Path

interface ImageClassApiDataSource {

    @GET("domains/{domain_id}/image_classes")
    suspend fun imageClassList(
        @Path(value = "domain_id") domainId: Int
    ): List<ImageClassResponseDto>

}