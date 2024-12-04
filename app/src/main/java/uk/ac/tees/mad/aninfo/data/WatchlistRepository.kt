package uk.ac.tees.mad.aninfo.data

import uk.ac.tees.mad.aninfo.models.Anime
import uk.ac.tees.mad.aninfo.models.toEntity
import javax.inject.Inject

interface WatchlistRepository {
    suspend fun addAnimeToWatchlist(anime: Anime)
    suspend fun removeAnimeFromWatchlist(anime: Anime)
    suspend fun isAnimeInWatchlist(animeId: Int): Boolean
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
}
