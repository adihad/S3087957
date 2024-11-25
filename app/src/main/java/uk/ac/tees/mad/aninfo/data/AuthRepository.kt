package uk.ac.tees.mad.aninfo.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

interface AuthRepository {
    suspend fun login(email: String, password: String): AuthResult
    suspend fun register(name: String, email: String, password: String): AuthResult
}

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : AuthRepository {

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
}


data class AuthResult(
    val success: Boolean,
    val errorMessage: String?
)
