package br.com.argmax.imagelabeling.service

interface ApiRequestCallback<T> {

    fun onSuccess(result: T)

    fun onError(errorMessage: String)

}