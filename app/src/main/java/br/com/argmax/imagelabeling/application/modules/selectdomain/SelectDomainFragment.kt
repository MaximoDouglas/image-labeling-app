package br.com.argmax.imagelabeling.application.modules.selectdomain

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
import br.com.argmax.imagelabeling.application.components.domaincreationdialog.DomainCreationDialog
import br.com.argmax.imagelabeling.application.components.domaincreationdialog.DomainCreationDialog.Companion.DOMAIN_CREATION_DIALOG_TAG
import br.com.argmax.imagelabeling.application.components.domaincreationdialog.DomainCreationDialogClickListener
import br.com.argmax.imagelabeling.application.modules.selectdomain.SelectDomainFragmentDirections.actionSelectDomainFragmentToDomainDetailFragment
import br.com.argmax.imagelabeling.application.modules.selectdomain.SelectDomainViewModel.SelectDomainViewModelState
import br.com.argmax.imagelabeling.application.modules.selectdomain.adapters.SelectDomainAdapter
import br.com.argmax.imagelabeling.application.modules.selectdomain.listeners.OnDomainCardClickListener
import br.com.argmax.imagelabeling.databinding.SelectDomainFragmentBinding
import br.com.argmax.imagelabeling.service.entities.domain.DomainResponseDto
import br.com.argmax.imagelabeling.utils.ViewModelFactoryProvider
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class SelectDomainFragment : DaggerFragment() {

    @Inject
    lateinit var mViewModelFactoryProvider: ViewModelFactoryProvider
    private var mViewModel: SelectDomainViewModel? = null

    private var mBinding: SelectDomainFragmentBinding? = null
    private val mDomainCreationDialog = DomainCreationDialog()

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
        mBinding = inflate(inflater, R.layout.select_domain_fragment, container, false)

        initViewModel()

        return mBinding?.root
    }

    private fun initViewModel() {
        mViewModel = ViewModelProvider(this, mViewModelFactoryProvider)
            .get(SelectDomainViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupFloatingActionButton()
        setupRecyclerView()
        setupViewModel()
    }

    private fun setupFloatingActionButton() {
        mDomainCreationDialog.setOkButtonClickListener(object : DomainCreationDialogClickListener {
            override fun onConfirm(editTextContent: String) {
                mViewModel?.createDomain(editTextContent)
            }
        })

        mBinding?.selectDomainFragmentFloatingActionButton?.setOnClickListener {
            showDomainDialogCreation()
        }
    }

    private fun showDomainDialogCreation() {
        mDomainCreationDialog.show(childFragmentManager, DOMAIN_CREATION_DIALOG_TAG)
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
                print(viewModelState.throwable.localizedMessage)
            }

            is SelectDomainViewModelState.GetDomainListSuccess -> {
                mAdapter.replaceDomainList(viewModelState.data)
                hideProgressBar()
            }

            is SelectDomainViewModelState.CreateDomainSuccess -> {
                hideProgressBar()
                navigateToDomainDetailFragment(viewModelState.data)
            }
        }
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