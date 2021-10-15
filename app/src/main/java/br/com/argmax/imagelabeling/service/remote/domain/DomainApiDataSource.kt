package br.com.argmax.imagelabeling.service.remote.domain

import br.com.argmax.imagelabeling.service.entities.domain.DomainRequestDto
import br.com.argmax.imagelabeling.service.entities.domain.DomainResponseDto
import retrofit2.Response
import retrofit2.http.*

interface DomainApiDataSource {

    @GET("domains/")
    suspend fun domainList(): List<DomainResponseDto>

    @POST("domains/")
    suspend fun createDomain(
        @Body request: DomainRequestDto
    ): DomainResponseDto

    @PUT("domains/{domain_id}")
    suspend fun editDomain(
        @Path(value = "domain_id") domainId: Int,
        @Body domainRequestDto: DomainRequestDto
    ): DomainResponseDto

    @DELETE("domains/{domain_id}")
    suspend fun deleteDomain(
        @Path(value = "domain_id") domainId: Int
    ): Response<Unit>

}