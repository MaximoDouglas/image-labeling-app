package br.com.argmax.imagelabeling.application.components.domaincard

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import br.com.argmax.imagelabeling.R
import br.com.argmax.imagelabeling.databinding.ComponentDomainCardBinding
import br.com.argmax.imagelabeling.service.entities.domain.DomainResponseDto
import com.google.android.material.card.MaterialCardView

class DomainCardComponent @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyle: Int = 0
) : MaterialCardView(context, attrs, defStyle) {

    private var mBinding: ComponentDomainCardBinding? = DataBindingUtil.inflate(
        LayoutInflater.from(context), R.layout.component_domain_card, this, true
    )

    fun setDomain(domainResponseDto: DomainResponseDto) {
        mBinding?.domain = domainResponseDto
        mBinding?.executePendingBindings()
    }

}