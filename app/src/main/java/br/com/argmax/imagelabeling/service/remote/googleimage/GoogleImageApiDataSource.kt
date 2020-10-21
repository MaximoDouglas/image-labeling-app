package br.com.argmax.imagelabeling.service.remote.googleimage

import br.com.argmax.imagelabeling.service.entities.googleimage.SerpApiResult
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
interface GoogleImageApiDataSource {

    @GET("images/search")
    suspend fun googleImageList(
        @Query(value = "q") searchTerm: String
    ): SerpApiResult

}

