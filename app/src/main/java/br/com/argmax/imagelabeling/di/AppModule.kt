package br.com.argmax.imagelabeling.di

import br.com.argmax.imagelabeling.BuildConfig
import br.com.argmax.imagelabeling.service.remote.domain.DomainApiDataSource
import br.com.argmax.imagelabeling.service.remote.domain.DomainRemoteDataSource
import br.com.argmax.imagelabeling.service.remote.domain.DomainRemoteDataSourceImpl
import br.com.argmax.imagelabeling.service.remote.googleimage.GoogleImageApiDataSource
import br.com.argmax.imagelabeling.service.remote.googleimage.GoogleImageRemoteDataSource
import br.com.argmax.imagelabeling.service.remote.googleimage.GoogleImageRemoteDataSourceImpl
import br.com.argmax.imagelabeling.service.remote.imageclass.ImageClassApiDataSource
import br.com.argmax.imagelabeling.service.remote.imageclass.ImageClassRemoteDataSource
import br.com.argmax.imagelabeling.service.remote.imageclass.ImageClassRemoteDataSourceImpl
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit.Builder
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
object AppModule {

    @Singleton
    @Provides
    @JvmStatic
    fun provideDomainRemoteDataSource(domainApiDataSource: DomainApiDataSource): DomainRemoteDataSource {
        return DomainRemoteDataSourceImpl(domainApiDataSource)
    }

    @Singleton
    @Provides
    @JvmStatic
    fun provideDomainApiDataSource(retrofitBuilder: Builder): DomainApiDataSource {
        return retrofitBuilder
            .baseUrl(BuildConfig.BASE_URL).build()
            .create(DomainApiDataSource::class.java)
    }

    @Singleton
    @Provides
    @JvmStatic
    fun provideImageClassRemoteDataSource(imageClassApiDataSource: ImageClassApiDataSource): ImageClassRemoteDataSource {
        return ImageClassRemoteDataSourceImpl(imageClassApiDataSource)
    }

    @Singleton
    @Provides
    @JvmStatic
    fun provideImageClassApiDataSource(retrofitBuilder: Builder): ImageClassApiDataSource {
        return retrofitBuilder
            .baseUrl(BuildConfig.BASE_URL).build()
            .create(ImageClassApiDataSource::class.java)
    }

    @Singleton
    @Provides
    @JvmStatic
    fun provideGoogleImageRemoteDataSource(googleImageApiDataSource: GoogleImageApiDataSource): GoogleImageRemoteDataSource {
        return GoogleImageRemoteDataSourceImpl(googleImageApiDataSource)
    }

    @Singleton
    @Provides
    @JvmStatic
    fun provideGoogleImageApiDataSource(retrofitBuilder: Builder): GoogleImageApiDataSource {
        return retrofitBuilder
            .baseUrl(BuildConfig.SERP_BASE_URL).build()
            .create(GoogleImageApiDataSource::class.java)
    }

}

@Module
object RemoteServiceModule {

    @Singleton
    @Provides
    @JvmStatic
    fun provideRetrofitBuilder(gsonBuilder: Gson, httpClient: OkHttpClient.Builder): Builder {
        return Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gsonBuilder))
            .client(httpClient.build())
    }

    @Singleton
    @Provides
    @JvmStatic
    fun provideGsonBuilder(): Gson {
        return GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create()
    }

    @Singleton
    @Provides
    @JvmStatic
    fun provideOkHttpClientBuilder(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient.Builder {
        val builder = OkHttpClient.Builder().readTimeout(15, TimeUnit.SECONDS)
        builder.addInterceptor(loggingInterceptor)

        return builder
    }

    @Singleton
    @Provides
    @JvmStatic
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        return loggingInterceptor
    }

}