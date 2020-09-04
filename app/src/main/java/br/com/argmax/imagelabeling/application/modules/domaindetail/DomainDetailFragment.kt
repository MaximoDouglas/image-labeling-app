package br.com.argmax.imagelabeling.application.modules.domaindetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil.inflate
import br.com.argmax.imagelabeling.R
import br.com.argmax.imagelabeling.databinding.DomainDetailFragmentBinding
import dagger.android.support.DaggerFragment

class DomainDetailFragment : DaggerFragment() {

    private var mBinding: DomainDetailFragmentBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        mBinding = inflate(inflater, R.layout.domain_detail_fragment, container, false)

        return mBinding?.root
    }

}