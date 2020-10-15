package br.com.argmax.imagelabeling.application.imageclassification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil.inflate
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import br.com.argmax.imagelabeling.R
import br.com.argmax.imagelabeling.databinding.FragmentImageClassificationBinding
import br.com.argmax.imagelabeling.utils.ViewModelFactoryProvider
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class ImageClassificationFragment : DaggerFragment() {

    @Inject
    lateinit var mViewModelFactoryProvider: ViewModelFactoryProvider
    private var mViewModel: ImageClassificationViewModel? = null

    private var mBinding: FragmentImageClassificationBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        mBinding = inflate(inflater, R.layout.fragment_select_domain, container, false)

        initViewModel()

        return mBinding?.root
    }

    private fun initViewModel() {
        mViewModel = ViewModelProvider(this, mViewModelFactoryProvider)
            .get(ImageClassificationViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModel()
    }

    private fun setupViewModel() {
        mViewModel?.getStateLiveData()?.removeObservers(viewLifecycleOwner)

        mViewModel?.getStateLiveData()?.observe(
            viewLifecycleOwner,
            Observer { viewModelState ->
                handleViewModelState(viewModelState)
            })

        mViewModel?.getDomainList()
    }

    private fun handleViewModelState(viewModelState: ImageClassificationViewModelState) {
        when (viewModelState) {
            is ImageClassificationViewModelState.Loading -> {
                if (mAdapter.itemCount == 0) {
                    mBinding?.contentLoadingProgressBar?.visibility = View.VISIBLE
                }
            }

            is ImageClassificationViewModelState.Error -> {
                hideProgressBar()
                print(viewModelState.throwable.localizedMessage)
            }

            is ImageClassificationViewModelState.GetImageSuccess -> {
                hideProgressBar()
            }

            is ImageClassificationViewModelState.SetImageClassificationSuccess -> {
                hideProgressBar()
            }
        }
    }

    private fun hideProgressBar() {
        mBinding?.contentLoadingProgressBar?.visibility = View.GONE
    }

}