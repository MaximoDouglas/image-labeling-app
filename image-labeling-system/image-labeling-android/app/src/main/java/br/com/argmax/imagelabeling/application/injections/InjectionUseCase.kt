package br.com.argmax.imagelabeling.application.injections

import br.com.argmax.imagelabeling.application.injections.InjectionRemoteDataSource.provideDomainRemoteDataSource
import br.com.argmax.imagelabeling.domain.usecases.domain.GetDomainUseCase

object InjectionUseCase {

    fun provideGetDomainUseCase(): GetDomainUseCase {
        return GetDomainUseCase(provideDomainRemoteDataSource())
    }


}