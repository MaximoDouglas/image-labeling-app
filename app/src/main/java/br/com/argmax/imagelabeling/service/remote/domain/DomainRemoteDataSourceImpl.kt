package br.com.argmax.imagelabeling.service.remote.domain

import br.com.argmax.imagelabeling.service.entities.Domain
import br.com.argmax.imagelabeling.service.entities.DomainRequestDto

class DomainRemoteDataSourceImpl(
    private val mDomainApiDataSource: DomainApiDataSource
) : DomainRemoteDataSource {

    override suspend fun domainList(): List<Domain> {
        return mDomainApiDataSource.domainList()
    }

    override suspend fun createDomain(domainRequestDto: DomainRequestDto): Domain {
        return mDomainApiDataSource.createDomain(domainRequestDto)
    }

}