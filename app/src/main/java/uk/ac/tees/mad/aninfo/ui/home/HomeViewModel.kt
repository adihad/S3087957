package uk.ac.tees.mad.aninfo.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import uk.ac.tees.mad.aninfo.data.AnimeRepository
import uk.ac.tees.mad.aninfo.data.ApiResponse
import uk.ac.tees.mad.aninfo.models.Anime
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val animeRepository: AnimeRepository
) : ViewModel() {

    private val _animeList = MutableStateFlow<ApiResponse>(ApiResponse(emptyList(), null))
    val animeList = _animeList.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    private var currentQuery: String? = null

    init {
        fetchTopAnime()
    }

    private fun fetchTopAnime() {
        viewModelScope.launch {
            _isLoading.value = true
            val anime = animeRepository.getTopAnime()
            _animeList.value = anime
            _isLoading.value = false
        }
    }

    fun searchAnime(query: String) {
        if (query.isEmpty() && currentQuery != null) {
            fetchTopAnime()
            currentQuery = null
        } else {
            viewModelScope.launch {
                _isLoading.value = true
                _animeList.value = animeRepository.searchAnime(query)
                _isLoading.value = false
                currentQuery = query
            }
        }
    }
}
