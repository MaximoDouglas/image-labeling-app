package br.com.argmax.imagelabeling.application.components.ghostbutton

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import br.com.argmax.imagelabeling.R
import br.com.argmax.imagelabeling.R.drawable.ghost_button_red_background_selector
import br.com.argmax.imagelabeling.databinding.ComponentGhostButtonBinding

class GhostButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyle: Int = 0
) : LinearLayout(context, attrs, defStyle) {

    private var mAsphaltGhostButtonComponentViewBinding: ComponentGhostButtonBinding? =
        DataBindingUtil.inflate(
            LayoutInflater.from(context), R.layout.component_ghost_button, this, true
        )

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)

        mAsphaltGhostButtonComponentViewBinding?.asphaltGhostButtonLabelTextView?.setTextColor(
            if (enabled) {
                ContextCompat.getColor(context, R.color.GREEN50)
            } else {
                ContextCompat.getColor(context, R.color.BLACK40)
            }
        )

        mAsphaltGhostButtonComponentViewBinding?.asphaltGhostButtonContainer?.setBackgroundResource(
            if (enabled) {
                R.drawable.ghost_button_green_background_selector
            } else {
                R.drawable.ghost_button_disabled_shape_black40
            }
        )
    }

    fun setText(text: String) {
        mAsphaltGhostButtonComponentViewBinding?.asphaltGhostButtonLabelTextView?.text = text
        mAsphaltGhostButtonComponentViewBinding?.executePendingBindings()
    }

    fun getText(): CharSequence? {
        return mAsphaltGhostButtonComponentViewBinding?.asphaltGhostButtonLabelTextView?.text
    }

    fun isConfirmationButton(isConfirmationButton: Boolean) {
        if (!isConfirmationButton) {
            mAsphaltGhostButtonComponentViewBinding?.asphaltGhostButtonLabelTextView?.setTextColor(
                R.drawable.ghost_button_text_red_selector
            )

            mAsphaltGhostButtonComponentViewBinding?.asphaltGhostButtonContainer?.setBackgroundResource(
                ghost_button_red_background_selector
            )
        }
    }


}