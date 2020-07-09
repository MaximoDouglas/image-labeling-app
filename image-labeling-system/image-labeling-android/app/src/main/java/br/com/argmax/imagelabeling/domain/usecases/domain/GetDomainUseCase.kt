package br.com.argmax.imagelabeling.domain.usecases.domain

import br.com.argmax.imagelabeling.domain.entities.Domain
import br.com.argmax.imagelabeling.service.remote.domain.DomainRemoteDataSource

class GetDomainUseCase(private val mDomainRemoteDataSource: DomainRemoteDataSource) {

    fun getDomainList() : List<Domain> {
        return mDomainRemoteDataSource.domainList()
    }

}