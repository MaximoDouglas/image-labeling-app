package br.com.argmax.imagelabeling.application.modules.selectdomain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.argmax.imagelabeling.service.entities.Domain
import br.com.argmax.imagelabeling.service.entities.DomainRequestDto
import br.com.argmax.imagelabeling.service.remote.domain.DomainRemoteDataSource
import br.com.argmax.imagelabeling.utils.CoroutineContextProvider
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SelectDomainViewModel @Inject constructor(
    private val mDomainRemoteDataSource: DomainRemoteDataSource,
    private val contextProvider: CoroutineContextProvider
) : ViewModel() {

    private val stateLiveData = MutableLiveData<SelectDomainViewModelState>()

    fun getStateLiveData(): LiveData<SelectDomainViewModelState> = stateLiveData

    private val handler = CoroutineExceptionHandler { _, exception ->
        stateLiveData.value = SelectDomainViewModelState.Error(exception)
    }

    fun getDomainList() {
        stateLiveData.value = SelectDomainViewModelState.Loading

        viewModelScope.launch(handler) {
            val data = withContext(contextProvider.IO) {
                mDomainRemoteDataSource.domainList()
            }

            stateLiveData.value = SelectDomainViewModelState.GetDomainListSuccess(data)
        }
    }

    fun createDomain(domainDescription: String) {
        stateLiveData.value = SelectDomainViewModelState.Loading

        val domainRequestDto = DomainRequestDto(domainDescription)

        viewModelScope.launch(handler) {
            val data = withContext(contextProvider.IO) {
                mDomainRemoteDataSource.createDomain(domainRequestDto)
            }

            stateLiveData.value = SelectDomainViewModelState.CreateDomainSuccess(data)
        }
    }

    sealed class SelectDomainViewModelState {
        object Loading : SelectDomainViewModelState()
        data class Error(val throwable: Throwable) : SelectDomainViewModelState()
        data class GetDomainListSuccess(val data: List<Domain>) : SelectDomainViewModelState()
        data class CreateDomainSuccess(val data: Domain) : SelectDomainViewModelState()
    }

}