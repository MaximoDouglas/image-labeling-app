package br.com.argmax.imagelabeling.service.remote.domain

import br.com.argmax.imagelabeling.service.entities.Domain
import br.com.argmax.imagelabeling.service.entities.DomainRequestDto

interface DomainRemoteDataSource {

    suspend fun domainList(): List<Domain>

    suspend fun createDomain(domainRequestDto: DomainRequestDto): Domain

}