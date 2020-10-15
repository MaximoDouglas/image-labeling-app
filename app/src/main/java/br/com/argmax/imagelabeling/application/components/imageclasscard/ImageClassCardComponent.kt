package br.com.argmax.imagelabeling.application.components.imageclasscard

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import br.com.argmax.imagelabeling.R
import br.com.argmax.imagelabeling.databinding.ComponentImageClassCardBinding
import br.com.argmax.imagelabeling.service.entities.imageclass.ImageClassResponseDto
import com.google.android.material.card.MaterialCardView

class ImageClassCardComponent @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyle: Int = 0
) : MaterialCardView(context, attrs, defStyle) {

    private var mBinding: ComponentImageClassCardBinding? = DataBindingUtil.inflate(
        LayoutInflater.from(context), R.layout.component_image_class_card, this, true
    )

    fun setImageClassResponseDto(imageClassResponseDto: ImageClassResponseDto) {
        mBinding?.imageClassResponseDto = imageClassResponseDto
        mBinding?.executePendingBindings()
    }

    fun getImageClassResponseDto(): ImageClassResponseDto? {
        return mBinding?.imageClassResponseDto
    }

}