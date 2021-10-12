package br.com.argmax.imagelabeling.application.selectdomain.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import br.com.argmax.imagelabeling.R
import br.com.argmax.imagelabeling.application.selectdomain.adapters.SelectDomainAdapter.DomainViewHolder
import br.com.argmax.imagelabeling.application.selectdomain.listeners.OnDomainCardClickListener
import br.com.argmax.imagelabeling.databinding.ViewHolderDomainCardBinding
import br.com.argmax.imagelabeling.service.entities.domain.DomainResponseDto
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.view_holder_domain_card.*

class SelectDomainAdapter(
    val onDomainCardClickListener: OnDomainCardClickListener
) : Adapter<DomainViewHolder>() {

    private var mData: List<DomainResponseDto> = listOf()

    fun replaceDomainList(domainResponseDtoList: List<DomainResponseDto>) {
        mData = domainResponseDtoList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DomainViewHolder {
        val domainCardViewHolderBinding: ViewHolderDomainCardBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.view_holder_domain_card,
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
    ) : RecyclerView.ViewHolder(itemView), LayoutContainer, View.OnClickListener {

        override val containerView: View?
            get() = itemView

        init {
            itemView.setOnClickListener(this)
        }

        fun updateData(domainResponseDto: DomainResponseDto) {
            domainCardComponent.setDomain(domainResponseDto)
        }

        override fun onClick(view: View?) {
            onDomainCardClickListener.onCardClick(mData[adapterPosition])
        }
    }

}