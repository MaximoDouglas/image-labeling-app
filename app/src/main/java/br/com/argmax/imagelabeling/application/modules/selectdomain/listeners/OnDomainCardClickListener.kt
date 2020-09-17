package br.com.argmax.imagelabeling.application.modules.selectdomain.listeners

import br.com.argmax.imagelabeling.service.entities.domain.DomainResponseDto

interface OnDomainCardClickListener {

    fun onCardClick(domainResponseDto: DomainResponseDto)

}
