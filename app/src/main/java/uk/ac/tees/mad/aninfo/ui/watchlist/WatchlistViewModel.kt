package uk.ac.tees.mad.aninfo.ui.watchlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import uk.ac.tees.mad.aninfo.data.WatchlistRepository
import uk.ac.tees.mad.aninfo.models.Anime
import uk.ac.tees.mad.aninfo.models.AnimeEntity
import javax.inject.Inject

@HiltViewModel
class WatchlistViewModel @Inject constructor(
    private val repository: WatchlistRepository
) : ViewModel() {

    suspend fun removeAnimeFromWatchlist(anime: Anime) {
        repository.removeAnimeFromWatchlist(anime)
    }

    val watchlist: StateFlow<List<AnimeEntity>> = repository.getWatchlist()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
}
