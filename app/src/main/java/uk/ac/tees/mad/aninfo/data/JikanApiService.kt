package uk.ac.tees.mad.aninfo.data

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import uk.ac.tees.mad.aninfo.models.JikanResponse

interface JikanApiService {

    @GET("top/anime")
    suspend fun getTopAnime(): Response<JikanResponse>

    @GET("anime")
    suspend fun searchAnime(@Query("q") query: String): Response<JikanResponse>

    @GET("anime")
    suspend fun getAnimeById(@Query("id") animeId: String): Response<JikanResponse>
}
