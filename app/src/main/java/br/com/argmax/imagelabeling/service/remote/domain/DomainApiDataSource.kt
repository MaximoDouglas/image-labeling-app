package br.com.argmax.imagelabeling.service.remote.domain

import br.com.argmax.imagelabeling.domain.entities.Domain
import io.reactivex.Observable
import retrofit2.http.GET

interface DomainApiDataSource {

    @GET("domains/")
    fun domainList(): Observable<List<Domain>>

}