package uk.ac.tees.mad.aninfo.ui.profile

import android.net.Uri
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
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val auth: FirebaseAuth,
    private val storage: FirebaseStorage
) : ViewModel() {

    private val _userProfile = MutableStateFlow<User?>(null)
    val userProfile = _userProfile.asStateFlow()


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
                userRepository.updateUserProfile(it)
            }
            _isLoading.value = false
        }
    }

    fun logout() {
        viewModelScope.launch {
            auth.signOut()
        }
    }
}

