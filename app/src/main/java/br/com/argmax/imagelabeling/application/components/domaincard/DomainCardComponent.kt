package br.com.argmax.imagelabeling.application.components.domaincard

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import br.com.argmax.imagelabeling.R
import br.com.argmax.imagelabeling.databinding.DomainCardComponentBinding
import br.com.argmax.imagelabeling.service.entities.Domain
import com.google.android.material.card.MaterialCardView

class DomainCardComponent @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyle: Int = 0
) : MaterialCardView(context, attrs, defStyle) {

    private var mBinding: DomainCardComponentBinding? = DataBindingUtil.inflate(
        LayoutInflater.from(context), R.layout.domain_card_component, this, true
    )

    fun setDomain(domain: Domain) {
        mBinding?.domain = domain
        mBinding?.executePendingBindings()
    }

}