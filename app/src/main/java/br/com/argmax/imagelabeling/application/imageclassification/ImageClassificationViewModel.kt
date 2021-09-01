package br.com.argmax.imagelabeling.application.imageclassification

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.argmax.imagelabeling.service.entities.image.ImageRequestDto
import br.com.argmax.imagelabeling.service.entities.imageclass.ImageClassRequestDto
import br.com.argmax.imagelabeling.service.entities.imageclass.ImageClassResponseDto
import br.com.argmax.imagelabeling.service.entities.rapidapientities.RapidApiImageResponseDto
import br.com.argmax.imagelabeling.service.remote.image.ImageRemoteDataSource
import br.com.argmax.imagelabeling.service.remote.imageclass.ImageClassRemoteDataSource
import br.com.argmax.imagelabeling.service.remote.rapidapiimage.RapidApiImageRemoteDataSource
import br.com.argmax.imagelabeling.utils.CoroutineContextProvider
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ImageClassificationViewModel @Inject constructor(
    private val mRapidApiImageRemoteDataSource: RapidApiImageRemoteDataSource,
    private val mImageRemoteDataSource: ImageRemoteDataSource,
    private val mImageClassRemoteDataSource: ImageClassRemoteDataSource,
    private val contextProvider: CoroutineContextProvider
) : ViewModel() {

    private val stateLiveData = MutableLiveData<ImageClassificationViewModelState>()
    fun getStateLiveData(): LiveData<ImageClassificationViewModelState> = stateLiveData

    private var offset: Int = 0

    private val handler = CoroutineExceptionHandler { _, exception ->
        stateLiveData.value = ImageClassificationViewModelState.Error(exception)
    }

    fun getRapidImage(searchTerm: String) {
        stateLiveData.value = ImageClassificationViewModelState.Loading

        viewModelScope.launch(handler) {
            val data = withContext(contextProvider.IO) {
                mRapidApiImageRemoteDataSource
                    .rapidApiImageListBySearchTerm(searchTerm, offset)
            }

            offset += data.size
            stateLiveData.value = ImageClassificationViewModelState.GetRapidImageSuccess(data)
        }
    }

    fun confirmImageClassification(
        rapidApiImageResponseDto: RapidApiImageResponseDto,
        imageClass: ImageClassResponseDto
    ) {
        viewModelScope.launch(handler) {
            withContext(contextProvider.IO) {
                mImageRemoteDataSource.sendImage(
                    ImageRequestDto(
                        url = rapidApiImageResponseDto.url,
                        imageClassId = imageClass.id
                    )
                )
            }
        }
    }

    fun editImageClassName(
        newImageClassName: String,
        imageClassResponseDto: ImageClassResponseDto
    ) {
        val imageClassDomainId = imageClassResponseDto.domainId
        val imageClassId = imageClassResponseDto.id

        val imageClassRequestDto = ImageClassRequestDto(
            name = newImageClassName,
            domainId = imageClassDomainId
        )

        viewModelScope.launch(handler) {
            val data = withContext(contextProvider.IO) {
                mImageClassRemoteDataSource.editImageClassName(
                    imageClassId = imageClassId,
                    imageClassRequestDto = imageClassRequestDto
                )
            }

            stateLiveData.value = ImageClassificationViewModelState.EditImageClassSuccess(data)
        }
    }

    fun deleteImageClass(imageClassId: Int) {
        viewModelScope.launch(handler) {
            withContext(contextProvider.IO) {
                mImageClassRemoteDataSource.deleteImageClass(
                    imageClassId = imageClassId
                )
            }

            stateLiveData.value = ImageClassificationViewModelState.DeleteImageClassSuccess
        }
    }

    sealed class ImageClassificationViewModelState {
        object Loading : ImageClassificationViewModelState()

        data class Error(val throwable: Throwable) : ImageClassificationViewModelState()

        data class GetRapidImageSuccess(val data: List<RapidApiImageResponseDto>?) :
            ImageClassificationViewModelState()

        data class EditImageClassSuccess(val data: ImageClassResponseDto) :
            ImageClassificationViewModelState()

        object DeleteImageClassSuccess : ImageClassificationViewModelState()
    }

}