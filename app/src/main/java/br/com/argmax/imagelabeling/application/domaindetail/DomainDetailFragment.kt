package br.com.argmax.imagelabeling.application.domaindetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil.inflate
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.argmax.imagelabeling.R
import br.com.argmax.imagelabeling.application.components.modelcreationdialog.ModelCreationDialog
import br.com.argmax.imagelabeling.application.components.modelcreationdialog.ModelCreationDialogClickListener
import br.com.argmax.imagelabeling.application.domaindetail.DomainDetailViewModel.DomainDetailViewModelState
import br.com.argmax.imagelabeling.application.domaindetail.adapters.ImageClassAdapter
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
    private val mImageClassCreationDialog = ModelCreationDialog()
    private val mDomainEditDialog = ModelCreationDialog()

    private val mAdapter = ImageClassAdapter()
    private var mDomainResponseDto: DomainResponseDto? = null

    private val args: DomainDetailFragmentArgs by navArgs()

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

        setDomainDataIntoView()
        setupViewModelObserver()
        setupEditButton()
        setupRecyclerView()
        setupImageClassCreationDialog()
    }

    private fun setupEditButton() {
        mDomainEditDialog.setTitle(getString(R.string.domain_detail_fragment_edit_dialog_title))
        mDomainEditDialog.setHint(getString(R.string.domain_detail_fragment_edit_dialog_hint))

        mDomainEditDialog.setOkButtonClickListener(object : ModelCreationDialogClickListener {
            override fun onConfirm(editTextContent: String) {
                mDomainResponseDto?.id?.let { domainId ->
                    mViewModel?.editDomain(editTextContent, domainId)
                }
            }
        })

        mBinding?.domainDetailFragmentDomainDescriptionEditImageView?.setOnClickListener {
            showDomainEditDialog()
        }
    }

    private fun showDomainEditDialog() {
        mDomainEditDialog.show(childFragmentManager,
            ModelCreationDialog.MODEL_CREATION_DIALOG_TAG
        )
    }

    private fun setDomainDataIntoView() {
        if (mDomainResponseDto != null) {
            val domainId = mDomainResponseDto?.id ?: 0
            val domainDescription = mDomainResponseDto?.description ?: ""

            mBinding?.domainDetailFragmentDomainIdTextView?.text = domainId.toString()
            mBinding?.domainDetailFragmentDomainDescriptionTextView?.text = domainDescription
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
        when (viewModelState) {
            is DomainDetailViewModelState.Loading -> {
                if (mAdapter.itemCount == 0) {
                    mBinding?.contentLoadingProgressBar?.visibility = View.VISIBLE
                }
            }

            is DomainDetailViewModelState.Error -> {
                hideProgressBar()
                print(viewModelState.throwable.localizedMessage)
            }

            is DomainDetailViewModelState.GetImageClassListSuccess -> {
                mAdapter.replaceData(viewModelState.data)
                hideProgressBar()
            }

            is DomainDetailViewModelState.CreateImageClassSuccess -> {
                mAdapter.addImageClass(viewModelState.data)
                hideProgressBar()
            }
        }
    }

    private fun hideProgressBar() {
        mBinding?.contentLoadingProgressBar?.visibility = View.GONE
    }

    private fun setupRecyclerView() {
        val spanCount = 2
        val gridLayoutManager = GridLayoutManager(context, spanCount, RecyclerView.VERTICAL, false)

        mBinding?.domainDetailFragmentRecyclerView?.layoutManager = gridLayoutManager
        mBinding?.domainDetailFragmentRecyclerView?.adapter = mAdapter
    }

    private fun setupImageClassCreationDialog() {
        mImageClassCreationDialog.setTitle(getString(R.string.domain_detail_fragment_image_class_creation_dialog_title))
        mImageClassCreationDialog.setHint(getString(R.string.domain_detail_fragment_image_class_creation_dialog_hint))

        mImageClassCreationDialog.setOkButtonClickListener(object : ModelCreationDialogClickListener {
            override fun onConfirm(editTextContent: String) {
                mDomainResponseDto?.id?.let { domainId ->
                    mViewModel?.createImageClass(editTextContent, domainId)
                }
            }
        })

        mBinding?.domainDetailFragmentFloatingActionButton?.setOnClickListener {
            showImageClassCreationDialog()
        }
    }

    private fun showImageClassCreationDialog() {
        mImageClassCreationDialog.show(childFragmentManager,
            ModelCreationDialog.MODEL_CREATION_DIALOG_TAG
        )
    }

}