package br.com.argmax.imagelabeling.service.remote.domain

import br.com.argmax.imagelabeling.service.entities.domain.DomainRequestDto
import br.com.argmax.imagelabeling.service.entities.domain.DomainResponseDto

interface DomainRemoteDataSource {

    suspend fun domainList(): List<DomainResponseDto>

    suspend fun createDomain(domainRequestDto: DomainRequestDto): DomainResponseDto

    suspend fun editDomain(domainId: Int, domainRequestDto: DomainRequestDto): DomainResponseDto

    suspend fun deleteDomain(domainId: Int)

}