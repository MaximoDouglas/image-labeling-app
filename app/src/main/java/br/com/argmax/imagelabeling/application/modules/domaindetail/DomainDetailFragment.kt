package br.com.argmax.imagelabeling.application.modules.domaindetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil.inflate
import androidx.navigation.fragment.navArgs
import br.com.argmax.imagelabeling.R
import br.com.argmax.imagelabeling.databinding.DomainDetailFragmentBinding
import br.com.argmax.imagelabeling.service.entities.Domain
import dagger.android.support.DaggerFragment

class DomainDetailFragment : DaggerFragment() {

    private var mBinding: DomainDetailFragmentBinding? = null
    private var mDomain: Domain? = null

    private val args: DomainDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        mBinding = inflate(inflater, R.layout.domain_detail_fragment, container, false)

        unwrapArgs()

        return mBinding?.root
    }

    private fun unwrapArgs() {
        mDomain = args.domain
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (mDomain != null) {
            val domainId = mDomain?.id ?: 0
            val domainDescription = mDomain?.description ?: ""

            mBinding?.domainDetailsFragmentDomainIdTextView?.text = domainId.toString()
            mBinding?.domainDetailsFragmentDomainDescriptionTextView?.text = domainDescription
        }
    }

}