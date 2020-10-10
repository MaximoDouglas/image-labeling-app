package br.com.argmax.imagelabeling.application.domaindetail.listeners

import br.com.argmax.imagelabeling.service.entities.imageclass.ImageClassResponseDto

interface OnImageClassCardClickListener {

    fun onCardClick(imageCLassResponseDto: ImageClassResponseDto)

}
