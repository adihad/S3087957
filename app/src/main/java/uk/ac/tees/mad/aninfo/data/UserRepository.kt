package uk.ac.tees.mad.aninfo.data

import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    suspend fun getCurrentUserProfile(): User? {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return null
        val document = firestore.collection("users").document(userId).get().await()
        return document.toObject(User::class.java)
    }
}
