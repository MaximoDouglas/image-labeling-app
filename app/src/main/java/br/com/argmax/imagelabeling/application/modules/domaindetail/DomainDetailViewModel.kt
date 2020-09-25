package br.com.argmax.imagelabeling.application.modules.domaindetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.argmax.imagelabeling.service.entities.imageclass.ImageClassRequestDto
import br.com.argmax.imagelabeling.service.entities.imageclass.ImageClassResponseDto
import br.com.argmax.imagelabeling.service.remote.imageclass.ImageClassRemoteDataSource
import br.com.argmax.imagelabeling.utils.CoroutineContextProvider
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DomainDetailViewModel @Inject constructor(
    private val mImageClassRemoteDataSource: ImageClassRemoteDataSource,
    private val contextProvider: CoroutineContextProvider
) : ViewModel() {

    private val stateLiveData = MutableLiveData<DomainDetailViewModelState>()

    fun getStateLiveData(): LiveData<DomainDetailViewModelState> = stateLiveData

    private val handler = CoroutineExceptionHandler { _, exception ->
        stateLiveData.value = DomainDetailViewModelState.Error(exception)
    }

    fun getImageClassListByDomainId(domainId: Int) {
        stateLiveData.value = DomainDetailViewModelState.Loading

        viewModelScope.launch(handler) {
            val data = withContext(contextProvider.IO) {
                mImageClassRemoteDataSource.imageClassListByDomainId(domainId)
            }

            stateLiveData.value = DomainDetailViewModelState.GetImageClassListSuccess(data)
        }
    }

    fun createImageClass(imageClassName: String, domainId: Int) {
        stateLiveData.value = DomainDetailViewModelState.Loading

        val imageClassRequestDto = ImageClassRequestDto(name = imageClassName, domainId = domainId)

        viewModelScope.launch(handler) {
            val data = withContext(contextProvider.IO) {
                mImageClassRemoteDataSource.createImageClass(imageClassRequestDto)
            }

            stateLiveData.value = DomainDetailViewModelState.CreateImageClassSuccess(data)
        }
    }

    sealed class DomainDetailViewModelState {
        object Loading : DomainDetailViewModelState()

        data class Error(val throwable: Throwable) : DomainDetailViewModelState()

        data class GetImageClassListSuccess(val data: List<ImageClassResponseDto>) :
            DomainDetailViewModelState()

        data class CreateImageClassSuccess(val data: ImageClassResponseDto) :
            DomainDetailViewModelState()
    }

}