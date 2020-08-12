package br.com.argmax.imagelabeling.application.components.domaincreationdialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil.inflate
import androidx.fragment.app.DialogFragment
import br.com.argmax.imagelabeling.R
import br.com.argmax.imagelabeling.databinding.DomainCreationDialogBinding

class DomainCreationDialog : DialogFragment() {

    private var mBinding: DomainCreationDialogBinding? = null
    private var mDomainCreationDialogClickListener: DomainCreationDialogClickListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        mBinding = inflate(inflater, R.layout.domain_creation_dialog, container, false)

        return mBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupButtons()
    }

    private fun setupButtons() {
        mBinding?.domainCreationDialogCancelTextView?.setOnClickListener {
            this.dismiss()
        }

        mBinding?.domainCreationDialogOkTextView?.setOnClickListener {
            mDomainCreationDialogClickListener?.onConfirm(
                mBinding?.domainCreationDialogEditText?.text?.toString() ?: ""
            )
            this.dismiss()
        }
    }

    fun setOkButtonClickListener(domainCreationDialogClickListener: DomainCreationDialogClickListener) {
        mDomainCreationDialogClickListener = domainCreationDialogClickListener
    }

    companion object {
        const val DOMAIN_CREATION_DIALOG_TAG = "domain-creation-dialog-tag"
    }

}