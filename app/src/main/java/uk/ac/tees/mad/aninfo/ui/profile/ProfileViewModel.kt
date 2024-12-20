package uk.ac.tees.mad.aninfo.ui.profile

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import uk.ac.tees.mad.aninfo.data.UserRepository
import uk.ac.tees.mad.aninfo.models.User
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val auth: FirebaseAuth,
    private val storage: FirebaseStorage
) : ViewModel() {

    private val _userProfile = MutableStateFlow<User?>(null)
    val userProfile = _userProfile.asStateFlow()

    private val _updateProfileState = MutableStateFlow(false)
    val updateProfileState = _updateProfileState.asStateFlow()

    lateinit var tempImageUri: Uri

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    init {
        loadUserProfile()
    }

    fun loadUserProfile() {
        viewModelScope.launch {
            val user = userRepository.getCurrentUserProfile()
            _userProfile.value = user
        }
    }


    fun updateUserProfile(name: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val updatedUser = _userProfile.value?.copy(name = name)
            updatedUser?.let {
                userRepository.updateUserProfile(
                    user = it,
                    authResult = { result ->
                        if (result.success) {
                            _updateProfileState.value = result.success
                        } else {
                            Log.d(
                                "ProfileViewModel",
                                "Update profile error: ${result.errorMessage}"
                            )
                        }
                        _isLoading.value = false
                    }
                )
            }
        }
    }

    fun uploadProfilePicture(uri: Uri) {
        viewModelScope.launch {
            val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return@launch
            val ref = storage.reference.child("profile_pictures/$userId.jpg")
            ref.putFile(uri).await()
            val downloadUrl = ref.downloadUrl.await().toString()
            val updatedUser = _userProfile.value?.copy(profilePictureUrl = downloadUrl)
            updatedUser?.let {
                userRepository.updateUserProfile(
                    it, authResult = { result ->
                        if (result.success) {
                            loadUserProfile()
                        }
                    }
                )
            }
        }
    }

    fun createTempImageUri(context: Context) {
        val tempFile = File.createTempFile("profile_image", ".jpg", context.cacheDir).apply {
            createNewFile()
            deleteOnExit()
        }
        tempImageUri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.provider",
            tempFile
        )
    }

    fun logout() {
        viewModelScope.launch {
            auth.signOut()
        }
    }
}

