package br.com.argmax.imagelabeling.service.remote.domain

import br.com.argmax.imagelabeling.service.entities.domain.DomainResponseDto
import br.com.argmax.imagelabeling.service.entities.domain.DomainRequestDto

interface DomainRemoteDataSource {

    suspend fun domainList(): List<DomainResponseDto>

    suspend fun createDomain(domainRequestDto: DomainRequestDto): DomainResponseDto

}