package br.com.argmax.imagelabeling.service.remote.domain

import android.annotation.SuppressLint
import br.com.argmax.imagelabeling.service.ApiRequestCallback
import br.com.argmax.imagelabeling.service.entities.Domain
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DomainRemoteDataSourceImpl(
    private val mDomainApiDataSource: DomainApiDataSource
) : DomainRemoteDataSource {

    @SuppressLint("CheckResult")
    override fun domainList(apiRequestCallback: ApiRequestCallback<List<Domain>>) {
        mDomainApiDataSource
            .domainList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                apiRequestCallback.onSuccess(it)
            }, {
                apiRequestCallback.onError(it.localizedMessage)
            })
    }

}