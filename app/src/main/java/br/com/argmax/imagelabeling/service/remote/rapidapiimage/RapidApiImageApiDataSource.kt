package br.com.argmax.imagelabeling.service.remote.rapidapiimage

import br.com.argmax.imagelabeling.BuildConfig
import br.com.argmax.imagelabeling.service.entities.rapidapientities.RapidApiSearchResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface RapidApiImageApiDataSource {

    @GET("images/search")
    suspend fun rapidApiImagesSearch(
        @Query(value = "q") searchTerm: String,
        @Header(value = "x-rapidapi-host") apiHost: String = BuildConfig.RAPID_API_HOST,
        @Header(value = "x-rapidapi-key") apiKey: String = BuildConfig.RAPID_API_KEY
    ): RapidApiSearchResponse

}

