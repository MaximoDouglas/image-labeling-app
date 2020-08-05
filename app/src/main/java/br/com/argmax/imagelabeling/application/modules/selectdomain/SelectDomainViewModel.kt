package br.com.argmax.imagelabeling.application.modules.selectdomain

import androidx.lifecycle.ViewModel
import br.com.argmax.imagelabeling.service.remote.domain.DomainRemoteDataSource
import javax.inject.Inject

class SelectDomainViewModel @Inject constructor(
    val mDomainRemoteDataSource: DomainRemoteDataSource
) : ViewModel() {
}