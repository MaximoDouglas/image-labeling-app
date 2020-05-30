package br.com.argmax.imagelabeling.application.injections

import br.com.argmax.imagelabeling.application.injections.InjectionApiDataSource.provideDomainApiDataSource
import br.com.argmax.imagelabeling.service.remote.domain.DomainRemoteDataSource
import br.com.argmax.imagelabeling.service.remote.domain.DomainRemoteDataSourceImpl

object InjectionRemoteDataSource {

    fun provideDomainRemoteDataSource(): DomainRemoteDataSource {
        return DomainRemoteDataSourceImpl.getInstance(provideDomainApiDataSource())
    }


}