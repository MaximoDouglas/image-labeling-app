package br.com.argmax.imagelabeling.service.remote.domain

import br.com.argmax.imagelabeling.domain.entities.Domain
import br.com.argmax.imagelabeling.service.RemoteDataSourceCallback

interface DomainRemoteDataSource {

    fun domainList(callback: RemoteDataSourceCallback<List<Domain>>)


}