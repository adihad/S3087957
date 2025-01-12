package uk.ac.tees.mad.aninfo.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import uk.ac.tees.mad.aninfo.models.User
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) {
    suspend fun getCurrentUserProfile(): User? {
        val userId = auth.currentUser?.uid ?: return null
        val document = firestore.collection("users").document(userId).get().await()
        return document.toObject(User::class.java)
    }

    fun updateUserProfile(user: User, authResult: (AuthResult) -> Unit) {
        val userId = auth.currentUser?.uid ?: return
        firestore.collection("users").document(userId).set(user).addOnSuccessListener {
            authResult(AuthResult(true, "Profile updated successfully"))
        }.addOnFailureListener {
            authResult(AuthResult(false, "Failed to update profile"))
        }
    }
}