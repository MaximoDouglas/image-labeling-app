package br.com.argmax.imagelabeling.application.domaindetail

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil.inflate
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.argmax.imagelabeling.R
import br.com.argmax.imagelabeling.application.components.modelcreationdialog.ModelCreationDialog
import br.com.argmax.imagelabeling.application.components.modelcreationdialog.ModelCreationDialogClickListener
import br.com.argmax.imagelabeling.application.domaindetail.DomainDetailViewModel.DomainDetailViewModelState
import br.com.argmax.imagelabeling.application.domaindetail.adapters.ImageClassAdapter
import br.com.argmax.imagelabeling.application.domaindetail.listeners.OnImageClassCardClickListener
import br.com.argmax.imagelabeling.databinding.FragmentDomainDetailBinding
import br.com.argmax.imagelabeling.service.entities.domain.DomainResponseDto
import br.com.argmax.imagelabeling.service.entities.imageclass.ImageClassResponseDto
import br.com.argmax.imagelabeling.utils.ViewModelFactoryProvider
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class DomainDetailFragment : DaggerFragment() {

    @Inject
    lateinit var mViewModelFactoryProvider: ViewModelFactoryProvider
    private var mViewModel: DomainDetailViewModel? = null

    private var mBinding: FragmentDomainDetailBinding? = null
    private val mImageClassCreationDialog = ModelCreationDialog()
    private val mDomainEditDialog = ModelCreationDialog()

    private var mDomainResponseDto: DomainResponseDto? = null
    private val args: DomainDetailFragmentArgs by navArgs()

    private val mAdapter = ImageClassAdapter(object : OnImageClassCardClickListener {
        override fun onCardClick(imageCLassResponseDto: ImageClassResponseDto) {
            navigateToImageClassDetailFragment(imageCLassResponseDto)
        }
    })

    private fun navigateToImageClassDetailFragment(imageCLassResponseDto: ImageClassResponseDto) {
        print(imageCLassResponseDto.name)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        mBinding = inflate(inflater, R.layout.fragment_domain_detail, container, false)

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
        setupToolbarBackButton()
        setupEditButton()
        setupDeleteButton()
        setupRecyclerView()
        setupImageClassCreationDialog()
    }

    private fun setupEditButton() {
        mDomainEditDialog.setOkButtonClickListener(object : ModelCreationDialogClickListener {
            override fun onConfirm(editTextContent: String) {
                mDomainResponseDto?.id?.let { domainId ->
                    mViewModel?.editDomain(editTextContent, domainId)
                }
            }
        })

        mBinding?.domainDetailFragmentDomainDescriptionEditIcon?.setOnClickListener {
            showDomainEditDialog()
        }
    }

    private fun showDomainEditDialog() {
        mDomainEditDialog.show(
            childFragmentManager,
            ModelCreationDialog.MODEL_CREATION_DIALOG_TAG
        )
    }

    private fun setupToolbarBackButton() {
        mBinding?.domainDetailFragmentToolbarBackIcon?.setOnClickListener {
            navigateBack()
        }
    }

    private fun navigateBack() {
        findNavController().navigateUp()
    }

    private fun setupDeleteButton() {
        mBinding?.domainDetailFragmentToolbarDeleteIcon?.setOnClickListener {
            AlertDialog.Builder(context)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(getString(R.string.domain_detail_fragment_delete_domain_dialog_title))
                .setMessage(getString(R.string.domain_detail_fragment_delete_domain_dialog_body))
                .setPositiveButton("Yes") { _, _ -> deleteDomain() }
                .setNegativeButton("No", null)
                .show()
        }
    }

    private fun deleteDomain() {
        mDomainResponseDto?.id?.let { mViewModel?.deleteDomain(it) }
    }

    private fun setDomainDataIntoView() {
        if (mDomainResponseDto != null) {
            val domainId = (mDomainResponseDto?.id ?: 0).toString()
            val domainDescription = mDomainResponseDto?.description ?: ""

            mBinding?.domainDetailFragmentToolbarTitle?.text = domainDescription
            mBinding?.domainDetailFragmentDomainIdTextView?.text = domainId
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

            is DomainDetailViewModelState.EditDomainSuccess -> {
                mDomainResponseDto = viewModelState.data
                setDomainDataIntoView()
                hideProgressBar()
            }

            is DomainDetailViewModelState.DeleteDomainSuccess -> {
                navigateBack()
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
       mImageClassCreationDialog.setOkButtonClickListener(object :
           ModelCreationDialogClickListener {
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
        mImageClassCreationDialog.show(
            childFragmentManager,
            ModelCreationDialog.MODEL_CREATION_DIALOG_TAG
        )
    }

}