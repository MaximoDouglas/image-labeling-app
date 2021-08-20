package br.com.argmax.imagelabeling.application.components.ghostbutton

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.content.ContextCompat.getColor
import androidx.databinding.DataBindingUtil
import br.com.argmax.imagelabeling.R
import br.com.argmax.imagelabeling.databinding.ComponentGhostButtonBinding

class GhostButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyle: Int = 0
) : LinearLayout(context, attrs, defStyle) {

    private var mIsConfirmationButton: Boolean = false

    private var mAsphaltGhostButtonComponentViewBinding: ComponentGhostButtonBinding? =
        DataBindingUtil.inflate(
            LayoutInflater.from(context), R.layout.component_ghost_button, this, true
        )

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)

        paintView()
    }

    private fun paintView() {
        if (this.isEnabled && mIsConfirmationButton) {
            applyColors(
                textColor = getColor(context, R.color.GREEN50),
                backgroundResource = R.drawable.ghost_button_shape_green60
            )
        } else if (this.isEnabled && !mIsConfirmationButton) {
            applyColors(
                textColor = getColor(context, R.color.RED40),
                backgroundResource = R.drawable.ghost_button_shape_red60
            )
        } else {
            applyColors(
                textColor = getColor(context, R.color.BLACK40),
                backgroundResource = R.drawable.ghost_button_disabled_shape_black40
            )
        }
    }

    private fun applyColors(textColor: Int, backgroundResource: Int) {
        mAsphaltGhostButtonComponentViewBinding?.asphaltGhostButtonLabelTextView?.setTextColor(
            textColor
        )

        mAsphaltGhostButtonComponentViewBinding?.asphaltGhostButtonContainer?.setBackgroundResource(
            backgroundResource
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
        mIsConfirmationButton = isConfirmationButton

        paintView()
    }

}