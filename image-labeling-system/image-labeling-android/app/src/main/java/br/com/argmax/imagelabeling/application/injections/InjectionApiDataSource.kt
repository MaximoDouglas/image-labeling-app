package br.com.argmax.imagelabeling.application.injections

import br.com.argmax.imagelabeling.service.ApiDataSource.Companion.createService
import br.com.argmax.imagelabeling.service.remote.domain.DomainApiDataSource

object InjectionApiDataSource {

    fun provideDomainApiDataSource(): DomainApiDataSource {
        return createService(DomainApiDataSource::class.java)
    }


}