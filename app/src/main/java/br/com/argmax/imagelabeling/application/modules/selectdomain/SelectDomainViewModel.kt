package br.com.argmax.imagelabeling.application.modules.selectdomain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.argmax.imagelabeling.application.injections.CoroutineContextProvider
import br.com.argmax.imagelabeling.domain.usecases.domain.GetDomainUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SelectDomainViewModel(
    private val getDomainUseCase: GetDomainUseCase,
    private val contextProvider: CoroutineContextProvider
) : ViewModel() {

    private val handler = CoroutineExceptionHandler { _, exception ->
        stateLiveData.value = SelectDomainViewState.Error(exception)
    }

    private val stateLiveData = MutableLiveData<SelectDomainViewState>()

    fun getStateLiveData(): LiveData<SelectDomainViewState> = stateLiveData

    fun getData() {
        stateLiveData.value = SelectDomainViewState.Loading
        viewModelScope.launch(handler) {
            val data = withContext(contextProvider.IO) {
                getDomainUseCase.getDomainList()
            }

            stateLiveData.value = SelectDomainViewState.Success(data)
        }
    }

    sealed class SelectDomainViewState {
        object Loading : SelectDomainViewState()
        data class Error(val throwable: Throwable) : SelectDomainViewState()
        data class Success(val data: Any) : SelectDomainViewState()
    }

}