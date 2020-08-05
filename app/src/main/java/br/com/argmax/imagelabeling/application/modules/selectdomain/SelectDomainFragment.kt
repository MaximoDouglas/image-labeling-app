package br.com.argmax.imagelabeling.application.modules.selectdomain

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil.inflate
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.argmax.imagelabeling.R
import br.com.argmax.imagelabeling.application.modules.selectdomain.adapters.SelectDomainAdapter
import br.com.argmax.imagelabeling.databinding.SelectDomainFragmentBinding
import br.com.argmax.imagelabeling.service.ApiRequestCallback
import br.com.argmax.imagelabeling.service.entities.Domain
import br.com.argmax.imagelabeling.service.remote.domain.DomainRemoteDataSource
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class SelectDomainFragment : DaggerFragment() {

    @Inject
    lateinit var mDomainRemoteDataSource: DomainRemoteDataSource

    private var mBinding: SelectDomainFragmentBinding? = null

    private val mAdapter = SelectDomainAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        mBinding = inflate(inflater, R.layout.select_domain_fragment, container, false)

        return mBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        callApi()
    }

    private fun setupRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        mBinding?.selectDomainFragmentRecyclerView?.layoutManager = linearLayoutManager
        mBinding?.selectDomainFragmentRecyclerView?.adapter = mAdapter
    }

    private fun callApi() {
        mDomainRemoteDataSource.domainList(object : ApiRequestCallback<List<Domain>> {
            override fun onSuccess(result: List<Domain>) {
                onRequestSuccess(result)
            }

            override fun onError(errorMessage: String) {
                onRequestError(errorMessage)
            }
        })
    }

    private fun onRequestSuccess(domainList: List<Domain>) {
        print(domainList.size)
        mAdapter.addDomainList(domainList)
    }

    private fun onRequestError(errorMessage: String) {
        print(errorMessage)
    }

}