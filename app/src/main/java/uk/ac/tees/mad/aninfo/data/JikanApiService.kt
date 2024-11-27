package uk.ac.tees.mad.aninfo.data

import retrofit2.Response
import retrofit2.http.GET
import uk.ac.tees.mad.aninfo.models.JikanResponse

interface JikanApiService {
    @GET("top/anime")
    suspend fun getTopAnime(): Response<JikanResponse>
}
