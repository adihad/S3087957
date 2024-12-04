package uk.ac.tees.mad.aninfo.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import uk.ac.tees.mad.aninfo.models.AnimeEntity

@Dao
interface WatchlistDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnime(anime: AnimeEntity)

    @Delete
    suspend fun deleteAnime(anime: AnimeEntity)

    @Query("SELECT EXISTS(SELECT 1 FROM anime_table WHERE mal_id = :animeId)")
    suspend fun isAnimeInWatchlist(animeId: Int): Boolean
}
