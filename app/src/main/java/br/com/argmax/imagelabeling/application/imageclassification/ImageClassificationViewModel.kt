package br.com.argmax.imagelabeling.application.imageclassification

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.argmax.imagelabeling.service.entities.googleimage.SerpApiImageResponseDto
import br.com.argmax.imagelabeling.service.remote.googleimage.GoogleImageRemoteDataSource
import br.com.argmax.imagelabeling.utils.CoroutineContextProvider
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ImageClassificationViewModel @Inject constructor(
    private val mGoogleImageRemoteDataSource: GoogleImageRemoteDataSource,
    private val contextProvider: CoroutineContextProvider
) : ViewModel() {

    private val stateLiveData = MutableLiveData<ImageClassificationViewModelState>()

    fun getStateLiveData(): LiveData<ImageClassificationViewModelState> = stateLiveData

    private val handler = CoroutineExceptionHandler { _, exception ->
        stateLiveData.value = ImageClassificationViewModelState.Error(exception)
    }

    fun getGoogleImage(searchTerm: String) {
        stateLiveData.value = ImageClassificationViewModelState.Loading

        viewModelScope.launch(handler) {
            val data = withContext(contextProvider.IO) {
                mGoogleImageRemoteDataSource.googleImageListBySearchTerm(searchTerm)
            }

            stateLiveData.value = ImageClassificationViewModelState.GetGoogleImageSuccess(data)
        }
    }

    sealed class ImageClassificationViewModelState {
        object Loading : ImageClassificationViewModelState()
        object SetImageClassificationSuccess : ImageClassificationViewModelState()
        data class Error(val throwable: Throwable) : ImageClassificationViewModelState()
        data class GetGoogleImageSuccess(val data: List<SerpApiImageResponseDto>?) :
            ImageClassificationViewModelState()
    }

}