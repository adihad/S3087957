package uk.ac.tees.mad.aninfo.ui.animedetail

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import uk.ac.tees.mad.aninfo.data.AnimeRepository
import uk.ac.tees.mad.aninfo.models.Anime
import javax.inject.Inject

@HiltViewModel
class AnimeDetailsViewModel @Inject constructor(
    private val animeRepository: AnimeRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val animeId: Int = checkNotNull(savedStateHandle.get<Int>("animeId"))

    init {
        loadAnimeDetails(animeId)
    }

    private val _anime = MutableStateFlow<Anime?>(null)
    val anime = _anime.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()


    private fun loadAnimeDetails(animeId: Int) {
        viewModelScope.launch {
            val anime = animeRepository.getAnimeById(animeId)
            Log.d("ANIM", anime.toString())

            _anime.value = anime.data
            _isLoading.value = false
        }
    }

}
