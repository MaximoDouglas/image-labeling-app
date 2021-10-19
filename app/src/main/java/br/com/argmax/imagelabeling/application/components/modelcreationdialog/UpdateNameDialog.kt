package br.com.argmax.imagelabeling.application.components.modelcreationdialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil.inflate
import androidx.fragment.app.DialogFragment
import br.com.argmax.imagelabeling.R
import br.com.argmax.imagelabeling.databinding.DialogModelCreationBinding

class UpdateNameDialog(
    private val mDialogTitle: String,
    private val mEditTextHint: String
) : DialogFragment() {

    private var mBinding: DialogModelCreationBinding? = null
    private var mModelCreationDialogClickListener: ModelCreationDialogClickListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        mBinding = inflate(inflater, R.layout.dialog_model_creation, container, false)
        setupViewData()

        return mBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupButtons()
    }

    private fun setupViewData() {
        mBinding?.modelCreationDialogTitleTextView?.text = mDialogTitle
        mBinding?.modelCreationDialogEditText?.hint = mEditTextHint
    }

    private fun setupButtons() {
        mBinding?.modelCreationDialogCancelTextView?.setOnClickListener {
            this.dismiss()
        }

        mBinding?.modelCreationDialogOkTextView?.setOnClickListener {
            mModelCreationDialogClickListener?.onConfirm(
                mBinding?.modelCreationDialogEditText?.text?.toString() ?: ""
            )
            this.dismiss()
        }
    }

    fun setOkButtonClickListener(modelCreationDialogClickListener: ModelCreationDialogClickListener) {
        mModelCreationDialogClickListener = modelCreationDialogClickListener
    }

    companion object {
        const val MODEL_CREATION_DIALOG_TAG = "model-creation-dialog-tag"
    }

}