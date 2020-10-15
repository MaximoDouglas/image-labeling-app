package br.com.argmax.imagelabeling.application.imageclassification

import android.media.Image
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.argmax.imagelabeling.application.selectdomain.SelectDomainViewModel
import br.com.argmax.imagelabeling.service.entities.domain.DomainResponseDto
import br.com.argmax.imagelabeling.utils.CoroutineContextProvider
import kotlinx.coroutines.CoroutineExceptionHandler
import javax.inject.Inject

class ImageClassificationViewModel @Inject constructor(
    private val contextProvider: CoroutineContextProvider
) : ViewModel() {

    private val stateLiveData = MutableLiveData<ImageClassificationViewModelState>()

    fun getStateLiveData(): LiveData<ImageClassificationViewModelState> = stateLiveData

    private val handler = CoroutineExceptionHandler { _, exception ->
        stateLiveData.value = ImageClassificationViewModelState.Error(exception)
    }

    fun getImage() {}

    sealed class ImageClassificationViewModelState {
        object Loading : ImageClassificationViewModelState()
        object SetImageClassificationSuccess : ImageClassificationViewModelState()
        data class Error(val throwable: Throwable) : ImageClassificationViewModelState()
        data class GetImageSuccess(val data: Any?) : ImageClassificationViewModelState()
    }

}