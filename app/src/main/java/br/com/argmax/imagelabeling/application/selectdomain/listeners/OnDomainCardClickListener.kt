package br.com.argmax.imagelabeling.application.selectdomain.listeners

import br.com.argmax.imagelabeling.service.entities.domain.DomainResponseDto

interface OnDomainCardClickListener {

    fun onCardClick(domainResponseDto: DomainResponseDto)

}
