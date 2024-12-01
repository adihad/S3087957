package uk.ac.tees.mad.aninfo.data

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.protobuf.Api
import kotlinx.coroutines.tasks.await
import uk.ac.tees.mad.aninfo.models.Anime
import javax.inject.Inject

interface AnimeRepository {
    suspend fun login(email: String, password: String): AuthResult
    suspend fun register(name: String, email: String, password: String): AuthResult
    suspend fun getTopAnime(): ListApiResponse
    suspend fun searchAnime(query: String): ListApiResponse
    suspend fun getAnimeById(id: Int): ApiResponse
}

class AnimeRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val apiService: JikanApiService
) : AnimeRepository {

    override suspend fun login(email: String, password: String): AuthResult {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            AuthResult(success = result.user != null, errorMessage = null)
        } catch (e: Exception) {
            AuthResult(success = false, errorMessage = e.message)
        }
    }

    override suspend fun register(name: String, email: String, password: String): AuthResult {
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            result.user?.let { user ->
                val userData = mapOf(
                    "name" to name,
                    "email" to email
                )
                firestore.collection("users").document(user.uid).set(userData).await()
            }
            AuthResult(success = true, errorMessage = null)
        } catch (e: Exception) {
            AuthResult(success = false, errorMessage = e.message)
        }
    }

    override suspend fun getTopAnime(): ListApiResponse {
        return try {
            val response = apiService.getTopAnime()
            val data = response.body()?.data
            ListApiResponse(data = data ?: emptyList(), errorMessage = null)
        } catch (e: Exception) {
            e.printStackTrace()
            ListApiResponse(data = emptyList(), errorMessage = e.message)
        }
    }

    override suspend fun searchAnime(query: String): ListApiResponse {
        return try {
            val response = apiService.searchAnime(query)
            val data = response.body()?.data
            ListApiResponse(data = data ?: emptyList(), errorMessage = null)
        } catch (e: Exception) {
            e.printStackTrace()
            ListApiResponse(data = emptyList(), errorMessage = e.message)
        }
    }

    override suspend fun getAnimeById(id: Int): ApiResponse {
        return try {
            val response = apiService.getAnimeById(id.toString())
            Log.d("ANIM", response.toString())
            val data = response.body()?.data
            ApiResponse(data = data, errorMessage = null)
        } catch (e: Exception) {
            e.printStackTrace()
            ApiResponse(data = null, errorMessage = e.message)
        }
    }
}

data class ListApiResponse(
    val data: List<Anime>,
    val errorMessage: String?
)

data class ApiResponse(
    val data: Anime?,
    val errorMessage: String?
)

data class AuthResult(
    val success: Boolean,
    val errorMessage: String?
)
