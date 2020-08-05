package br.com.argmax.imagelabeling.service.remote.domain

import br.com.argmax.imagelabeling.service.entities.Domain
import io.reactivex.Observable
import retrofit2.http.GET

interface DomainApiDataSource {

    @GET("domains/")
    suspend fun domainList(): List<Domain>

}