package br.com.argmax.imagelabeling.service.remote.image

import br.com.argmax.imagelabeling.service.entities.image.ImageRequestDto
import br.com.argmax.imagelabeling.service.entities.image.ImageResponseDto
import retrofit2.http.Body
import retrofit2.http.POST

interface ImageApiDataSource {

    @POST("images/")
    suspend fun sendImage(
        @Body imageRequestDto: ImageRequestDto
    ): ImageResponseDto

}