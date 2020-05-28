package br.com.argmax.imagelabeling.service

import br.com.argmax.imagelabeling.BuildConfig
import java.util.concurrent.TimeUnit

class ApiDataSource {

    companion object {

        fun <S> createService(serviceClass: Class<S>): S {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            val httpClient = OkHttpClient.Builder()
                .readTimeout(15, TimeUnit.SECONDS)

            httpClient.addInterceptor(loggingInterceptor)

            val gson = GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create()

            val retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient.build())
                .build()

            return retrofit.create(serviceClass)
        }

    }


}