package br.com.argmax.imagelabeling.di

import br.com.argmax.imagelabeling.BuildConfig
import br.com.argmax.imagelabeling.service.remote.domain.DomainApiDataSource
import br.com.argmax.imagelabeling.service.remote.domain.DomainRemoteDataSource
import br.com.argmax.imagelabeling.service.remote.domain.DomainRemoteDataSourceImpl
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
object AppModule {

    @Provides
    @JvmStatic
    fun provideDomainRemoteDataSource(domainApiDataSource: DomainApiDataSource): DomainRemoteDataSource {
        return DomainRemoteDataSourceImpl(domainApiDataSource)
    }

    @Provides
    @JvmStatic
    fun provideDomainApiDataSource(retrofit: Retrofit): DomainApiDataSource {
        return retrofit.create(DomainApiDataSource::class.java)
    }

}

@Module
object ServiceModule {

    @Provides
    @JvmStatic
    fun provideRetrofitService(gsonBuilder: Gson, httpClient: OkHttpClient.Builder): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gsonBuilder))
            .client(httpClient.build())
            .build()
    }

    @Provides
    @JvmStatic
    fun provideGsonBuilder(): Gson {
        return GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create()
    }

    @Provides
    @JvmStatic
    fun provideOkHttpClientBuilder(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient.Builder {
        val builder = OkHttpClient.Builder().readTimeout(15, TimeUnit.SECONDS)
        builder.addInterceptor(loggingInterceptor)

        return builder
    }

    @Provides
    @JvmStatic
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        return loggingInterceptor
    }

}