package br.com.argmax.imagelabeling.di

import br.com.argmax.imagelabeling.application.domaindetail.DomainDetailFragment
import br.com.argmax.imagelabeling.application.imageclassification.ImageClassificationFragment
import br.com.argmax.imagelabeling.application.selectdomain.SelectDomainFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuildersModule {

    @ContributesAndroidInjector(
        modules = [SelectDomainViewModelsModule::class]
    )
    abstract fun contributesSelectDomainFragment(): SelectDomainFragment

    @ContributesAndroidInjector(
        modules = [DomainDetailViewModelsModule::class]
    )
    abstract fun contributesDomainDetailFragment(): DomainDetailFragment

    @ContributesAndroidInjector(
        modules = [ImageClassificationViewModelsModule::class]
    )
    abstract fun contributesImageClassificationFragment(): ImageClassificationFragment

}