package br.com.argmax.imagelabeling.domain.usecases

interface LoadDataFromServiceCallback<R> {

    fun onSuccess(response: R)

    fun onError(errorDescription: String)

}