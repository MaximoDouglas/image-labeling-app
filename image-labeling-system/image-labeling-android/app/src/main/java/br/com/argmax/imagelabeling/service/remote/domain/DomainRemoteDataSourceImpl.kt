package br.com.argmax.imagelabeling.service.remote.domain

import androidx.annotation.NonNull
import br.com.argmax.imagelabeling.domain.entities.Domain
import br.com.argmax.imagelabeling.service.RemoteDataSourceCallback
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DomainRemoteDataSourceImpl private constructor(
    private val mDomainApiDataSource: DomainApiDataSource
) : DomainRemoteDataSource {

    companion object {

        @Volatile
        private var INSTANCE: DomainRemoteDataSourceImpl? = null

        fun getInstance(@NonNull domainApiDataSource: DomainApiDataSource): DomainRemoteDataSourceImpl =
            INSTANCE ?: synchronized(this) {
                DomainRemoteDataSourceImpl(domainApiDataSource).also { domainRemoteDataSourceImpl ->
                    INSTANCE = domainRemoteDataSourceImpl
                }
            }


    }

    override fun domainList(callback: RemoteDataSourceCallback<List<Domain>>) {
        mDomainApiDataSource
            .domainList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { callback.isLoading(true) }
            .doAfterTerminate { callback.isLoading(false) }
            .subscribe(
                { response ->
                    callback.onSuccess(response)
                }, { throwable ->
                    callback.onError(throwable.message.toString())
                }
            )
            .dispose()
    }


}