package br.com.argmax.imagelabeling.service.remote.domain

import br.com.argmax.imagelabeling.service.entities.Domain

interface DomainRemoteDataSource {

    suspend fun domainList(): List<Domain>

}