package br.com.argmax.imagelabeling.application.modules.selectdomain

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import br.com.argmax.imagelabeling.R
import br.com.argmax.imagelabeling.databinding.SelectDomainFragmentBinding
import br.com.argmax.imagelabeling.service.entities.Domain
import br.com.argmax.imagelabeling.service.ApiDataSource
import br.com.argmax.imagelabeling.service.remote.domain.DomainApiDataSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SelectDomainFragment : Fragment() {

    private var mBinding: SelectDomainFragmentBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        mBinding =
            DataBindingUtil.inflate(inflater, R.layout.select_domain_fragment, container, false)

        return mBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        foo()
        mBinding?.tryAgainButton?.setOnClickListener {
            foo()
        }
    }

    @SuppressLint("CheckResult")
    fun foo() {
        val apiDataSource = ApiDataSource.createService(DomainApiDataSource::class.java)

        apiDataSource.domainList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                onSuccess(it)
            }, {
                onError(it)
            })
    }

    private fun onSuccess(domainList: List<Domain>) {
        print(domainList.size)
    }

    private fun onError(throwable: Throwable) {
        print(throwable.message)
    }

}