package br.com.argmax.imagelabeling.application.modules.selectdomain

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil.inflate
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.argmax.imagelabeling.R
import br.com.argmax.imagelabeling.application.modules.selectdomain.SelectDomainViewModel.SelectDomainViewModelState
import br.com.argmax.imagelabeling.application.modules.selectdomain.adapters.SelectDomainAdapter
import br.com.argmax.imagelabeling.databinding.SelectDomainFragmentBinding
import br.com.argmax.imagelabeling.utils.ViewModelFactoryProvider
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class SelectDomainFragment : DaggerFragment() {

    @Inject
    lateinit var mViewModelFactoryProvider: ViewModelFactoryProvider

    private var mViewModel: SelectDomainViewModel? = null
    private var mBinding: SelectDomainFragmentBinding? = null
    private val mAdapter = SelectDomainAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        mBinding = inflate(inflater, R.layout.select_domain_fragment, container, false)

        mViewModel = ViewModelProvider(this, mViewModelFactoryProvider)
            .get(SelectDomainViewModel::class.java)

        return mBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupFloatingActionClickListener()
        setupRecyclerView()
        setupViewModel()
    }

    private fun setupFloatingActionClickListener() {
        mBinding?.selectDomainFragmentFloatingActionButton?.setOnClickListener {

        }
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
                    println("Is Loading")
                }
            }

            is SelectDomainViewModelState.Error -> {
                print(viewModelState.throwable.localizedMessage)
            }

            is SelectDomainViewModelState.Success -> {
                mAdapter.addDomainList(viewModelState.data)
            }
        }
    }

}