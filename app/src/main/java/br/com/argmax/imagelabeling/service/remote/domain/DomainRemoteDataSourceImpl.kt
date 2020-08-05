package br.com.argmax.imagelabeling.service.remote.domain

import br.com.argmax.imagelabeling.service.entities.Domain

class DomainRemoteDataSourceImpl(
    private val mDomainApiDataSource: DomainApiDataSource
) : DomainRemoteDataSource {

    override suspend fun domainList(): List<Domain> {
        return mDomainApiDataSource.domainList()
    }

}