package uk.ac.tees.mad.aninfo.ui.authentication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import uk.ac.tees.mad.aninfo.data.AnimeRepository
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val animeRepository: AnimeRepository
) : ViewModel() {

    private val _authState = MutableStateFlow(AuthState())
    val authState: StateFlow<AuthState> = _authState

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState(isLoading = true)
            val result = animeRepository.login(email, password)
            _authState.value = AuthState(
                isLoading = false,
                isAuthenticated = result.success,
                error = result.errorMessage
            )
        }
    }

    fun register(name: String, email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState(isLoading = true)
            val result = animeRepository.register(name, email, password)
            _authState.value = AuthState(
                isLoading = false,
                isAuthenticated = result.success,
                error = result.errorMessage
            )
        }
    }
}


data class AuthState(
    val isLoading: Boolean = false,
    val isAuthenticated: Boolean = false,
    val error: String? = null
)

