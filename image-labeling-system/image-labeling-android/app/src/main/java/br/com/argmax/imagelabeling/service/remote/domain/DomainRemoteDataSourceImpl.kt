package br.com.argmax.imagelabeling.service.remote.domain

import androidx.annotation.NonNull
import br.com.argmax.imagelabeling.domain.entities.Domain
import br.com.argmax.imagelabeling.service.RemoteDataSourceCallback

class DomainRemoteDataSourceImpl private constructor(
    private val domainApiDataSource: DomainApiDataSource
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
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}