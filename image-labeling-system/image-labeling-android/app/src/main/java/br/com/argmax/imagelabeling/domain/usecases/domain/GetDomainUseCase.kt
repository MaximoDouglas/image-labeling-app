package br.com.argmax.imagelabeling.domain.usecases.domain

import br.com.argmax.imagelabeling.domain.entities.Domain
import br.com.argmax.imagelabeling.domain.usecases.UseCaseCallback
import br.com.argmax.imagelabeling.service.RemoteDataSourceCallback
import br.com.argmax.imagelabeling.service.remote.domain.DomainRemoteDataSource

class GetDomainUseCase(private val mDomainRemoteDataSource: DomainRemoteDataSource) {

    fun getDomainList(useCaseCallback: UseCaseCallback<List<Domain>>) {
        mDomainRemoteDataSource.domainList(object : RemoteDataSourceCallback<List<Domain>> {
            override fun onSuccess(response: List<Domain>) {
                if (response.isEmpty()) {
                    useCaseCallback.onEmptyData()
                } else {
                    useCaseCallback.onSuccess(response)
                }
            }

            override fun isLoading(isLoading: Boolean) {
                useCaseCallback.isLoading(isLoading)
            }

            override fun onError(errorMessage: String) {
                useCaseCallback.onError(errorMessage)
            }
        })
    }

}