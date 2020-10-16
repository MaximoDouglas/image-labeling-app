package br.com.argmax.imagelabeling.application.imageclassification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil.inflate
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import br.com.argmax.imagelabeling.R
import br.com.argmax.imagelabeling.application.imageclassification.ImageClassificationViewModel.ImageClassificationViewModelState
import br.com.argmax.imagelabeling.databinding.FragmentImageClassificationBinding
import br.com.argmax.imagelabeling.service.entities.imageclass.ImageClassResponseDto
import br.com.argmax.imagelabeling.utils.ViewModelFactoryProvider
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class ImageClassificationFragment : DaggerFragment() {

    @Inject
    lateinit var mViewModelFactoryProvider: ViewModelFactoryProvider

    private var mViewModel: ImageClassificationViewModel? = null

    private val args: ImageClassificationFragmentArgs by navArgs()
    private var mImageClassResponseDto: ImageClassResponseDto? = null

    private var mBinding: FragmentImageClassificationBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        mBinding = inflate(inflater, R.layout.fragment_image_classification, container, false)

        unwrapArgs()
        initViewModel()

        return mBinding?.root
    }

    private fun unwrapArgs() {
        mImageClassResponseDto = args.imageCLassResponseDto
    }

    private fun initViewModel() {
        mViewModel = ViewModelProvider(this, mViewModelFactoryProvider)
            .get(ImageClassificationViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()

        setupViewModel()
    }

    private fun setupView() {
        setupToolbarBackNavigation()

        mImageClassResponseDto?.let {
            setImageClassDataIntoView(it)
        }
    }

    private fun setupToolbarBackNavigation() {
        mBinding?.toolbarBackIcon?.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setImageClassDataIntoView(imageClassResponseDto: ImageClassResponseDto) {
        val imageClassId = imageClassResponseDto.id.toString()
        val imageClassName = imageClassResponseDto.name

        mBinding?.toolbarTitle?.text = imageClassName
        mBinding?.imageClassIdTextView?.text = imageClassId
        mBinding?.imageClassNameTextView?.text = imageClassName
    }

    private fun setupViewModel() {
        mViewModel?.getStateLiveData()?.removeObservers(viewLifecycleOwner)

        mViewModel?.getStateLiveData()?.observe(
            viewLifecycleOwner,
            Observer { viewModelState ->
                handleViewModelState(viewModelState)
            })

        mViewModel?.getImage()
    }

    private fun handleViewModelState(viewModelState: ImageClassificationViewModelState) {
        when (viewModelState) {
            is ImageClassificationViewModelState.Loading -> {
                mBinding?.contentLoadingProgressBar?.visibility = View.VISIBLE
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