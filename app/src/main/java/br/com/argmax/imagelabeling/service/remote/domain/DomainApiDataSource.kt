package br.com.argmax.imagelabeling.service.remote.domain

import br.com.argmax.imagelabeling.service.entities.Domain
import br.com.argmax.imagelabeling.service.entities.DomainRequestDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface DomainApiDataSource {

    @GET("domains/")
    suspend fun domainList(): List<Domain>

    @POST("domains/")
    suspend fun createDomain(
        @Body request: DomainRequestDto
    ): Domain

}