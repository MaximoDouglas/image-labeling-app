package br.com.argmax.imagelabeling.service.remote.domain

import androidx.annotation.NonNull
import br.com.argmax.imagelabeling.service.entities.Domain
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

    override fun domainList() : List<Domain>{
        var data = listOf<Domain>()

        mDomainApiDataSource
            .domainList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { response -> data = response }
            .dispose()

        return data
    }

}