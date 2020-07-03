package br.com.argmax.imagelabeling.domain.usecases

interface UseCaseCallback<R> {

    fun onSuccess(response: R)

    fun onEmptyData()

    fun isLoading(isLoading: Boolean)

    fun onError(errorDescription: String)

}