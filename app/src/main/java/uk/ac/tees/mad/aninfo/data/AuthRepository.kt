package uk.ac.tees.mad.aninfo.data

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

interface AuthRepository {
    suspend fun login(email: String, password: String): AuthResult
    suspend fun register(email: String, password: String): AuthResult
}

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : AuthRepository {

    override suspend fun login(email: String, password: String): AuthResult {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            AuthResult(success = result.user != null, errorMessage = null)
        } catch (e: Exception) {
            AuthResult(success = false, errorMessage = e.message)
        }
    }

    override suspend fun register(email: String, password: String): AuthResult {
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            AuthResult(success = result.user != null, errorMessage = null)
        } catch (e: Exception) {
            AuthResult(success = false, errorMessage = e.message)
        }
    }
}

data class AuthResult(
    val success: Boolean,
    val errorMessage: String?
)
