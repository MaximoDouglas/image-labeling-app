package br.com.argmax.imagelabeling.di

import br.com.argmax.imagelabeling.service.ApiDataSource
import br.com.argmax.imagelabeling.service.remote.domain.DomainApiDataSource
import dagger.Module
import dagger.Provides

@Module
object AppModule {

    @Provides
    @JvmStatic
    fun provideDomainApiDataSource(): DomainApiDataSource {
        return ApiDataSource.createService(DomainApiDataSource::class.java)
    }

}