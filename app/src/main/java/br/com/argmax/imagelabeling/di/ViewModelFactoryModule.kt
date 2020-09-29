package br.com.argmax.imagelabeling.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.argmax.imagelabeling.application.domaindetail.DomainDetailViewModel
import br.com.argmax.imagelabeling.application.modules.selectdomain.SelectDomainViewModel
import br.com.argmax.imagelabeling.utils.ViewModelFactoryProvider
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import kotlin.reflect.KClass

@Module
abstract class ViewModelFactoryModule {

    @Binds
    abstract fun bindViewModelFactory(viewModelFactoryProvider: ViewModelFactoryProvider): ViewModelProvider.Factory

}

@Module
abstract class SelectDomainViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(SelectDomainViewModel::class)
    abstract fun bindsSelectDomainViewModel(selectDomainViewModel: SelectDomainViewModel): ViewModel

}

@Module
abstract class DomainDetailViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(DomainDetailViewModel::class)
    abstract fun bindsDomainDetailViewModel(domainDetailViewModel: DomainDetailViewModel): ViewModel

}

@MustBeDocumented
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)