package br.com.argmax.imagelabeling.application.domaindetail.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import br.com.argmax.imagelabeling.R
import br.com.argmax.imagelabeling.application.domaindetail.adapters.ImageClassAdapter.ImageClassCardViewHolder
import br.com.argmax.imagelabeling.application.domaindetail.listeners.OnImageClassCardClickListener
import br.com.argmax.imagelabeling.databinding.ComponentImageClassCardBinding
import br.com.argmax.imagelabeling.service.entities.imageclass.ImageClassResponseDto

class ImageClassAdapter(
    val mOnImageClassCardClickListener: OnImageClassCardClickListener
) : Adapter<ImageClassCardViewHolder>() {

    private var mData: MutableList<ImageClassResponseDto> = mutableListOf()

    fun addImageClass(imageClassResponseDto: ImageClassResponseDto) {
        mData.add(imageClassResponseDto)
        notifyItemChanged(itemCount)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun replaceData(imageClassResponseDtoList: List<ImageClassResponseDto>) {
        mData = imageClassResponseDtoList.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageClassCardViewHolder {
        val componentImageClassCardBinding: ComponentImageClassCardBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.component_image_class_card,
                parent,
                false
            )

        return ImageClassCardViewHolder(componentImageClassCardBinding)
    }

    override fun onBindViewHolder(holder: ImageClassCardViewHolder, position: Int) {
        holder.updateData(mData[position])
    }

    inner class ImageClassCardViewHolder(
        private val binding: ComponentImageClassCardBinding
    ) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        fun updateData(imageClassResponseDto: ImageClassResponseDto) {
            binding.imageClassResponseDto = imageClassResponseDto
            binding.clickListener = this
        }

        override fun onClick(p0: View?) {
            val imageClassResponseDto = binding.imageClassResponseDto
            imageClassResponseDto?.let {
                mOnImageClassCardClickListener.onCardClick(it)
            }
        }
    }

}