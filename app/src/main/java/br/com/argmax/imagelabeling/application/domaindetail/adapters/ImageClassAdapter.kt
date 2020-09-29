package br.com.argmax.imagelabeling.application.domaindetail.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import br.com.argmax.imagelabeling.R
import br.com.argmax.imagelabeling.application.domaindetail.adapters.ImageClassAdapter.ImageClassCardViewHolder
import br.com.argmax.imagelabeling.databinding.ImageClassCardViewHolderBinding
import br.com.argmax.imagelabeling.service.entities.imageclass.ImageClassResponseDto
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.image_class_card_view_holder.imageClassCardComponent

class ImageClassAdapter : Adapter<ImageClassCardViewHolder>() {

    private var mData: List<ImageClassResponseDto> = listOf()

    fun replaceData(imageClassResponseDtoList: List<ImageClassResponseDto>) {
        mData = imageClassResponseDtoList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageClassCardViewHolder {
        val imageClassViewHolderBinding: ImageClassCardViewHolderBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.image_class_card_view_holder,
                parent,
                false
            )

        return ImageClassCardViewHolder(imageClassViewHolderBinding.root)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onBindViewHolder(holder: ImageClassCardViewHolder, position: Int) {
        holder.updateData(mData[position])
    }

    inner class ImageClassCardViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView), LayoutContainer {

        override val containerView: View?
            get() = itemView

        fun updateData(imageClassResponseDto: ImageClassResponseDto) {
            imageClassCardComponent.setImageClassResponseDto(imageClassResponseDto)
        }
    }

}