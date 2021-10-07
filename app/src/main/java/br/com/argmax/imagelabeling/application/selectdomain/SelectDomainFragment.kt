package br.com.argmax.imagelabeling.application.selectdomain

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil.inflate
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.argmax.imagelabeling.R
import br.com.argmax.imagelabeling.application.components.modelcreationdialog.ModelCreationDialogClickListener
import br.com.argmax.imagelabeling.application.components.modelcreationdialog.UpdateNameDialog
import br.com.argmax.imagelabeling.application.components.modelcreationdialog.UpdateNameDialog.Companion.MODEL_CREATION_DIALOG_TAG
import br.com.argmax.imagelabeling.application.selectdomain.SelectDomainFragmentDirections.actionSelectDomainFragmentToDomainDetailFragment
import br.com.argmax.imagelabeling.application.selectdomain.SelectDomainViewModel.SelectDomainViewModelState
import br.com.argmax.imagelabeling.application.selectdomain.adapters.SelectDomainAdapter
import br.com.argmax.imagelabeling.application.selectdomain.listeners.OnDomainCardClickListener
import br.com.argmax.imagelabeling.databinding.FragmentSelectDomainBinding
import br.com.argmax.imagelabeling.service.entities.domain.DomainResponseDto
import br.com.argmax.imagelabeling.utils.ViewModelFactoryProvider
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class SelectDomainFragment : DaggerFragment() {

    @Inject
    lateinit var mViewModelFactoryProvider: ViewModelFactoryProvider
    private var mViewModel: SelectDomainViewModel? = null

    private var mBinding: FragmentSelectDomainBinding? = null
    private val mModelCreationDialog = UpdateNameDialog()

    private val mAdapter = SelectDomainAdapter(object : OnDomainCardClickListener {
        override fun onCardClick(domainResponseDto: DomainResponseDto) {
            navigateToDomainDetailFragment(domainResponseDto)
        }
    })

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
            .get(SelectDomainViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupButtons()
        setupRecyclerView()
        setupViewModel()
    }

    private fun setupButtons() {
        mModelCreationDialog.setOkButtonClickListener(object : ModelCreationDialogClickListener {
            override fun onConfirm(editTextContent: String) {
                mViewModel?.createDomain(editTextContent)
            }
        })

        mBinding?.selectDomainFragmentFloatingActionButton?.setOnClickListener {
            showModelCreationDialog()
        }

        mBinding?.tryAgainButton?.setOnClickListener {
            mViewModel?.getDomainList()
        }
    }

    private fun showModelCreationDialog() {
        mModelCreationDialog.show(childFragmentManager, MODEL_CREATION_DIALOG_TAG)
    }

    private fun setupRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        mBinding?.selectDomainFragmentRecyclerView?.layoutManager = linearLayoutManager
        mBinding?.selectDomainFragmentRecyclerView?.adapter = mAdapter
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

    private fun handleViewModelState(viewModelState: SelectDomainViewModelState) {
        when (viewModelState) {
            is SelectDomainViewModelState.Loading -> {
                if (mAdapter.itemCount == 0) {
                    mBinding?.contentLoadingProgressBar?.visibility = View.VISIBLE
                }
            }

            is SelectDomainViewModelState.Error -> {
                hideProgressBar()
                showErrorWhileFetchingDomainsView()
            }

            is SelectDomainViewModelState.GetDomainListSuccess -> {
                hideErrorView()
                mAdapter.replaceDomainList(viewModelState.data)
                hideProgressBar()
            }

            is SelectDomainViewModelState.CreateDomainSuccess -> {
                hideProgressBar()
                navigateToDomainDetailFragment(viewModelState.data)
            }
        }
    }

    private fun showErrorWhileFetchingDomainsView() {
        mBinding?.selectDomainFragmentRecyclerView?.visibility = View.GONE
        mBinding?.somethingWentWrongView?.visibility = View.VISIBLE
    }

    private fun hideErrorView() {
        mBinding?.somethingWentWrongView?.visibility = View.GONE
        mBinding?.selectDomainFragmentRecyclerView?.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        mBinding?.contentLoadingProgressBar?.visibility = View.GONE
    }

    private fun navigateToDomainDetailFragment(domainResponseDto: DomainResponseDto) {
        findNavController().navigate(
            actionSelectDomainFragmentToDomainDetailFragment(
                domainResponseDto
            )
        )
    }

}