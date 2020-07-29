package br.com.argmax.imagelabeling.application.modules.selectdomain

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import br.com.argmax.imagelabeling.R
import br.com.argmax.imagelabeling.databinding.SelectDomainFragmentBinding

class SelectDomainFragment : Fragment() {

    private var mBinding: SelectDomainFragmentBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        mBinding = DataBindingUtil.inflate(inflater, R.layout.select_domain_fragment, container, false)

        return mBinding?.root
    }

}