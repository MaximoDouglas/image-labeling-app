package br.com.argmax.imagelabeling.application.injections

import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class CoroutineContextProvider @Inject constructor() {
    open val Main: CoroutineContext by lazy { Dispatchers.Main }
    open val IO: CoroutineContext by lazy { Dispatchers.IO }
}