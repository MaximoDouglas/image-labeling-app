package br.com.argmax.imagelabeling.service

interface RemoteDataSourceCallback<R> {

    fun onSuccess(response: R)

    fun isLoading(isLoading: Boolean)

    fun onError(errorMessage: String)

}