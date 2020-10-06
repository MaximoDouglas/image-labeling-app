package br.com.argmax.imagelabeling.service.remote.domain

import br.com.argmax.imagelabeling.service.entities.domain.DomainResponseDto
import br.com.argmax.imagelabeling.service.entities.domain.DomainRequestDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface DomainApiDataSource {

    @GET("domains/")
    suspend fun domainList(): List<DomainResponseDto>

    @POST("domains/")
    suspend fun createDomain(
        @Body request: DomainRequestDto
    ): DomainResponseDto

    @PUT("domains/{domain_id}")
    fun editDomain(
        @Path(value = "domain_id") domainId: Int,
        @Body domainRequestDto: DomainRequestDto
    ): DomainResponseDto

}