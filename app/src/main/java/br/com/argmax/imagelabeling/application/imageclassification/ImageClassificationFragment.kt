package br.com.argmax.imagelabeling.application.imageclassification

import android.app.Activity
import android.app.AlertDialog
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView.OnEditorActionListener
import androidx.databinding.DataBindingUtil.inflate
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import br.com.argmax.imagelabeling.R
import br.com.argmax.imagelabeling.application.components.modelcreationdialog.ModelCreationDialogClickListener
import br.com.argmax.imagelabeling.application.components.modelcreationdialog.UpdateNameDialog
import br.com.argmax.imagelabeling.application.imageclassification.ImageClassificationViewModel.ImageClassificationViewModelState
import br.com.argmax.imagelabeling.databinding.FragmentImageClassificationBinding
import br.com.argmax.imagelabeling.service.entities.imageclass.ImageClassResponseDto
import br.com.argmax.imagelabeling.service.entities.rapidapientities.RapidApiImageResponseDto
import br.com.argmax.imagelabeling.utils.ViewModelFactoryProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class ImageClassificationFragment : DaggerFragment() {

    @Inject
    lateinit var mViewModelFactoryProvider: ViewModelFactoryProvider

    private var mViewModel: ImageClassificationViewModel? = null

    private val args: ImageClassificationFragmentArgs by navArgs()

    private var mImageClassResponseDto: ImageClassResponseDto? = null
    private var mBinding: FragmentImageClassificationBinding? = null

    private var mImageResponseDtoList = mutableListOf<RapidApiImageResponseDto>()

    private var mSearchTerm: String? = null
    private var mListPosition = 0

    private val mClassNameEditDialog = UpdateNameDialog()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        mBinding = inflate(inflater, R.layout.fragment_image_classification, container, false)

        unwrapArgs()
        initViewModel()

        return mBinding?.root
    }

    private fun unwrapArgs() {
        mImageClassResponseDto = args.imageCLassResponseDto
    }

    private fun initViewModel() {
        mViewModel = ViewModelProvider(this, mViewModelFactoryProvider)
            .get(ImageClassificationViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()
        setupViewModel()
        setupInteractions()
        setupEditButton()
    }

    private fun setupView() {
        mImageClassResponseDto?.let {
            setImageClassDataIntoView(it)
        }

        setupButtons()
    }

    private fun setImageClassDataIntoView(imageClassResponseDto: ImageClassResponseDto) {
        val imageClassId = imageClassResponseDto.id.toString()
        val imageClassName = imageClassResponseDto.name

        mBinding?.toolbarTitle?.text = imageClassName
        mBinding?.imageClassIdTextView?.text = imageClassId
        mBinding?.imageClassNameTextView?.text = imageClassName
    }

    private fun setupViewModel() {
        mViewModel?.getStateLiveData()?.removeObservers(viewLifecycleOwner)

        mViewModel?.getStateLiveData()?.observe(
            viewLifecycleOwner,
            Observer { viewModelState ->
                handleViewModelState(viewModelState)
            })
    }

    private fun handleViewModelState(viewModelState: ImageClassificationViewModelState) {
        when (viewModelState) {
            is ImageClassificationViewModelState.Loading -> {
                mBinding?.contentLoadingProgressBar?.visibility = View.VISIBLE
            }
            is ImageClassificationViewModelState.Error -> {
                hideProgressBar()
                print(viewModelState.throwable.localizedMessage)
            }
            is ImageClassificationViewModelState.GetRapidImageSuccess -> {
                hideProgressBar()
                changeSearchTermViewVisibility()

                viewModelState.data?.let {
                    mImageResponseDtoList.addAll(it)
                }

                updateImageView()
            }
            is ImageClassificationViewModelState.SendImageSuccess -> {
                hideProgressBar()
                incrementPosition()
                updateImageView()
            }
            is ImageClassificationViewModelState.EditImageClassSuccess -> {
                hideProgressBar()
                setImageClassDataIntoView(viewModelState.data)
            }
            is ImageClassificationViewModelState.DeleteImageClassSuccess -> {
                navigateUp()
            }
        }
    }

    private fun updateImageView() {
        mBinding?.imageView?.let { imageView ->
            context?.let { contextNotNull ->
                Glide.with(contextNotNull)
                    .load(mImageResponseDtoList[mListPosition].url)
                    .error(R.drawable.ic_broken_image)
                    .into(imageView)
            }
        }
    }

    private fun getGlideRequestListener(): RequestListener<Drawable> {
        return object: RequestListener<Drawable>{
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                onImageFetchFailed()
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                onImageFetchSuccess()
                return false
            }
        }
    }

    private fun onImageFetchFailed() {
        incrementPosition()
        showNextImage()
    }

    private fun onImageFetchSuccess() {
        stopLoadingImageAnimation()
    }

    private fun hideProgressBar() {
        mBinding?.contentLoadingProgressBar?.visibility = View.GONE
    }

    private fun setupInteractions() {
        setupToolbarBackNavigation()
        setupToolbarDeleteButton()
        setupSearchButtonClick()
    }

    private fun setupToolbarBackNavigation() {
        mBinding?.toolbarBackIcon?.setOnClickListener {
            navigateUp()
        }
    }

    private fun navigateUp() {
        findNavController().navigateUp()
    }

    private fun setupToolbarDeleteButton() {
        mBinding?.toolbarDeleteIcon?.setOnClickListener {
            AlertDialog.Builder(context)
                .setMessage(
                    getString(R.string.image_classification_fragment_delete_dialog_caution_text)
                )
                .setCancelable(true)
                .setPositiveButton(
                    getString(
                        R.string.image_classification_fragment_delete_dialog_yes_button_text
                    )
                ) { _, _ -> deleteImageClass() }
                .setNegativeButton(
                    getString(R.string.image_classification_fragment_delete_dialog_no_button_text),
                    null
                )
                .show()
        }
    }

    private fun deleteImageClass() {
        mImageClassResponseDto?.id?.let { imageClassResponse ->
            mViewModel?.deleteImageClass(imageClassResponse)
        }
    }

    private fun setupEditButton() {
        mClassNameEditDialog.setOkButtonClickListener(object : ModelCreationDialogClickListener {
            override fun onConfirm(editTextContent: String) {
                mImageClassResponseDto?.let { imageClassResponseDto ->
                    mViewModel?.editImageClassName(editTextContent, imageClassResponseDto)
                }
            }
        })

        mBinding?.imageClassNameEditIcon?.setOnClickListener {
            showClassNameEditDialog()
        }
    }

    private fun showClassNameEditDialog() {
        mClassNameEditDialog.show(
            childFragmentManager,
            UpdateNameDialog.MODEL_CREATION_DIALOG_TAG
        )
    }

    private fun setupSearchButtonClick() {
        mBinding?.searchTermSearchIcon?.setOnClickListener {
            mBinding?.searchTermEditText?.text.toString().let { searchTerm ->
                mViewModel?.getRapidImage(searchTerm)
                hideKeyboard()
            }
        }

        mBinding?.searchTermEditText?.setOnEditorActionListener(
            OnEditorActionListener { textView, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    val searchTerm = textView.text.toString()

                    mViewModel?.getRapidImage(searchTerm)

                    hideKeyboard()
                    return@OnEditorActionListener true
                }
                false
            }
        )
    }

    private fun hideKeyboard() {
        val inputMethodManager =
            context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as? InputMethodManager
        val flags = 0

        inputMethodManager?.hideSoftInputFromWindow(view?.windowToken, flags)
    }

    private fun changeSearchTermViewVisibility() {
        if (mBinding?.searchTermDefaultView?.visibility == View.GONE) {
            mSearchTerm = mBinding?.searchTermEditText?.text.toString()
            mBinding?.searchTermEditView?.visibility = View.GONE

            mBinding?.searchTermTextView?.text = mSearchTerm
            mBinding?.searchTermDefaultView?.visibility = View.VISIBLE
        } else {
            mBinding?.searchTermDefaultView?.visibility = View.GONE
            mBinding?.searchTermEditView?.visibility = View.VISIBLE
        }
    }

    private fun setupButtons() {
        mBinding?.discardButton?.setText(getString(R.string.image_classification_fragment_discard_button_label))
        mBinding?.discardButton?.isConfirmationButton(false)
        mBinding?.discardButton?.setOnClickListener {
            showNextImage()
        }

        mBinding?.confirmButton?.setText(getString(R.string.image_classification_fragment_confirm_button_label))
        mBinding?.discardButton?.isConfirmationButton(true)
        mBinding?.confirmButton?.setOnClickListener {
            confirmImageClassification()
            showNextImage()
        }
    }

    private fun showNextImage() {
        startLoadingImageAnimation()
        incrementPosition()

        val threshold = 10
        if (mListPosition == mImageResponseDtoList.size - threshold) {
            mSearchTerm?.let {
                mViewModel?.getRapidImage(searchTerm = it)
            }
        } else {
            updateImageView()
        }
    }

    private fun startLoadingImageAnimation() {
        mBinding?.imageView?.visibility = View.GONE
        mBinding?.imageLoadingProgressBar?.visibility = View.VISIBLE
    }

    private fun stopLoadingImageAnimation() {
        mBinding?.imageLoadingProgressBar?.visibility = View.GONE
        mBinding?.imageView?.visibility = View.VISIBLE
    }

    private fun confirmImageClassification() {
        mImageClassResponseDto?.let { imageClassResponseDto ->
            val rapidApiImageResponseDto = mImageResponseDtoList[mListPosition]

            mViewModel?.confirmImageClassification(
                rapidApiImageResponseDto,
                imageClassResponseDto
            )
        }
    }

    private fun incrementPosition() {
        mListPosition += 1
    }
}
