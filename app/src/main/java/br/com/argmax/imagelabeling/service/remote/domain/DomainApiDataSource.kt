package br.com.argmax.imagelabeling.service.remote.domain

import br.com.argmax.imagelabeling.service.entities.domain.DomainResponseDto
import br.com.argmax.imagelabeling.service.entities.domain.DomainRequestDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface DomainApiDataSource {

    @GET("domains/")
    suspend fun domainList(): List<DomainResponseDto>

    @POST("domains/")
    suspend fun createDomain(
        @Body request: DomainRequestDto
    ): DomainResponseDto

}