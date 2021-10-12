package br.com.argmax.imagelabeling.application.selectdomain.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import br.com.argmax.imagelabeling.R
import br.com.argmax.imagelabeling.application.selectdomain.adapters.SelectDomainAdapter.DomainViewHolder
import br.com.argmax.imagelabeling.application.selectdomain.listeners.OnDomainCardClickListener
import br.com.argmax.imagelabeling.databinding.ComponentDomainCardBinding
import br.com.argmax.imagelabeling.service.entities.domain.DomainResponseDto

class SelectDomainAdapter(
    val onDomainCardClickListener: OnDomainCardClickListener
) : Adapter<DomainViewHolder>() {

    private var mData: List<DomainResponseDto> = listOf()

    @SuppressLint("NotifyDataSetChanged")
    fun replaceDomainList(domainResponseDtoList: List<DomainResponseDto>) {
        mData = domainResponseDtoList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DomainViewHolder {
        val componentDomainCardBinding: ComponentDomainCardBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.component_domain_card,
                parent,
                false
            )

        return DomainViewHolder(componentDomainCardBinding)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onBindViewHolder(holder: DomainViewHolder, position: Int) {
        holder.updateData(mData[position])
    }

    inner class DomainViewHolder(
        private val binding: ComponentDomainCardBinding
    ) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        fun updateData(domainResponseDto: DomainResponseDto) {
            binding.domain = domainResponseDto
            binding.clickListener = this
        }

        override fun onClick(view: View?) {
            onDomainCardClickListener.onCardClick(mData[adapterPosition])
        }
    }

}