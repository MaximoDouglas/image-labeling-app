package br.com.argmax.imagelabeling.application.modules.selectdomain.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import br.com.argmax.imagelabeling.R
import br.com.argmax.imagelabeling.application.modules.selectdomain.adapters.SelectDomainAdapter.DomainViewHolder
import br.com.argmax.imagelabeling.databinding.DomainCardViewHolderBinding
import br.com.argmax.imagelabeling.service.entities.domain.DomainResponseDto
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.domain_card_view_holder.*

class SelectDomainAdapter : Adapter<DomainViewHolder>() {

    private var mData: MutableList<DomainResponseDto> = mutableListOf()

    fun addDomainList(domainResponseDtoList: List<DomainResponseDto>) {
        mData.addAll(domainResponseDtoList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DomainViewHolder {
        val domainCardViewHolderBinding: DomainCardViewHolderBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.domain_card_view_holder,
                parent,
                false
            )

        return DomainViewHolder(domainCardViewHolderBinding.root)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onBindViewHolder(holder: DomainViewHolder, position: Int) {
        holder.updateData(mData[position])
    }

    inner class DomainViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView), LayoutContainer {

        override val containerView: View?
            get() = itemView

        fun updateData(domainResponseDto: DomainResponseDto) {
            domainCardComponent.setDomain(domainResponseDto)
        }
    }

}