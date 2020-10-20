package br.com.argmax.imagelabeling.service.remote.googleimage

import br.com.argmax.imagelabeling.BuildConfig
import br.com.argmax.imagelabeling.service.entities.googleimage.SerpApiImageResults
import retrofit2.http.GET
import retrofit2.http.Path

interface GoogleImageApiDataSource {

    @GET(BuildConfig.SERP_BASE_URL + "playground")
    suspend fun googleImageList(
        @Path(value = "search_term") searchTerm: String
    ): SerpApiImageResults

}