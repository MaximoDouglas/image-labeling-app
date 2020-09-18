package br.com.argmax.imagelabeling.application.modules.domaindetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil.inflate
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import br.com.argmax.imagelabeling.R
import br.com.argmax.imagelabeling.application.modules.domaindetail.DomainDetailViewModel.DomainDetailViewModelState
import br.com.argmax.imagelabeling.databinding.DomainDetailFragmentBinding
import br.com.argmax.imagelabeling.service.entities.domain.DomainResponseDto
import br.com.argmax.imagelabeling.utils.ViewModelFactoryProvider
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class DomainDetailFragment : DaggerFragment() {

    @Inject
    lateinit var mViewModelFactoryProvider: ViewModelFactoryProvider
    private var mViewModel: DomainDetailViewModel? = null

    private var mBinding: DomainDetailFragmentBinding? = null

    private val args: DomainDetailFragmentArgs by navArgs()
    private var mDomainResponseDto: DomainResponseDto? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        mBinding = inflate(inflater, R.layout.domain_detail_fragment, container, false)

        unwrapArgs()
        initViewModel()

        return mBinding?.root
    }

    private fun unwrapArgs() {
        mDomainResponseDto = args.domainResponseDto
    }

    private fun initViewModel() {
        mViewModel = ViewModelProvider(
            this,
            mViewModelFactoryProvider
        ).get(DomainDetailViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setBundleDataIntoView()
        setupViewModelObserver()
    }

    private fun setBundleDataIntoView() {
        if (mDomainResponseDto != null) {
            val domainId = mDomainResponseDto?.id ?: 0
            val domainDescription = mDomainResponseDto?.description ?: ""

            mBinding?.domainDetailsFragmentDomainIdTextView?.text = domainId.toString()
            mBinding?.domainDetailsFragmentDomainDescriptionTextView?.text = domainDescription
        }
    }

    private fun setupViewModelObserver() {
        mViewModel?.getStateLiveData()?.observe(
            viewLifecycleOwner,
            Observer { viewModelState ->
                handleViewModelState(viewModelState)
            })

        mDomainResponseDto?.id?.let {
            mViewModel?.getImageClassListByDomainId(it)
        }
    }

    private fun handleViewModelState(viewModelState: DomainDetailViewModelState?) {
        print(viewModelState)
    }

}
