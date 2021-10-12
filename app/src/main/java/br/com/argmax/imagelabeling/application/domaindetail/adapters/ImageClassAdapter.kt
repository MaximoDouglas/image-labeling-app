package br.com.argmax.imagelabeling.application.domaindetail.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import br.com.argmax.imagelabeling.R
import br.com.argmax.imagelabeling.application.domaindetail.adapters.ImageClassAdapter.ImageClassCardViewHolder
import br.com.argmax.imagelabeling.application.domaindetail.listeners.OnImageClassCardClickListener
import br.com.argmax.imagelabeling.databinding.ViewHolderImageClassCardBinding
import br.com.argmax.imagelabeling.service.entities.imageclass.ImageClassResponseDto
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.view_holder_image_class_card.*

class ImageClassAdapter(
    val mOnImageClassCardClickListener: OnImageClassCardClickListener
) : Adapter<ImageClassCardViewHolder>() {

    private var mData: MutableList<ImageClassResponseDto> = mutableListOf()

    fun replaceData(imageClassResponseDtoList: List<ImageClassResponseDto>) {
        mData = imageClassResponseDtoList.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageClassCardViewHolder {
        val imageClassViewHolderBinding: ViewHolderImageClassCardBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.view_holder_image_class_card,
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

    fun addImageClass(imageClassResponseDto: ImageClassResponseDto) {
        mData.add(imageClassResponseDto)
        notifyItemChanged(itemCount)
    }

    inner class ImageClassCardViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView), LayoutContainer, View.OnClickListener {

        override val containerView: View?
            get() = itemView

        init {
            imageClassCardComponent.setOnClickListener(this)
        }

        fun updateData(imageClassResponseDto: ImageClassResponseDto) {
            imageClassCardComponent.setImageClassResponseDto(imageClassResponseDto)
        }

        override fun onClick(p0: View?) {
            val imageClassResponseDto = imageClassCardComponent.getImageClassResponseDto()
            imageClassResponseDto?.let {
                mOnImageClassCardClickListener.onCardClick(it)
            }
        }
    }

}