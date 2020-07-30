package br.com.argmax.imagelabeling.service.remote.domain

import br.com.argmax.imagelabeling.service.entities.Domain

interface DomainRemoteDataSource {

    fun domainList() : List<Domain>

}