package uk.ac.tees.mad.aninfo.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import uk.ac.tees.mad.aninfo.models.Anime
import uk.ac.tees.mad.aninfo.models.AnimeEntity
import uk.ac.tees.mad.aninfo.models.toEntity
import javax.inject.Inject

interface WatchlistRepository {
    suspend fun addAnimeToWatchlist(anime: Anime)
    suspend fun removeAnimeFromWatchlist(anime: Anime)
    suspend fun isAnimeInWatchlist(animeId: Int): Boolean
    fun getWatchlist(): Flow<List<AnimeEntity>>
}

class WatchlistRepositoryImpl @Inject constructor(
    private val watchlistDao: WatchlistDao
) : WatchlistRepository {

    override suspend fun addAnimeToWatchlist(anime: Anime) {
        watchlistDao.insertAnime(anime.toEntity())
    }

    override suspend fun removeAnimeFromWatchlist(anime: Anime) {
        watchlistDao.deleteAnime(anime.toEntity())
    }

    override suspend fun isAnimeInWatchlist(animeId: Int): Boolean {
        return watchlistDao.isAnimeInWatchlist(animeId)
    }

    override fun getWatchlist(): Flow<List<AnimeEntity>> {
        return watchlistDao.getWatchlist()
    }
}
