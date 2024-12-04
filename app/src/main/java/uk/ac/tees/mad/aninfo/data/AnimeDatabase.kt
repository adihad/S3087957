package uk.ac.tees.mad.aninfo.data

import androidx.room.Database
import androidx.room.RoomDatabase
import uk.ac.tees.mad.aninfo.models.AnimeEntity

@Database(
    entities = [AnimeEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AnimeDatabase: RoomDatabase() {
    abstract fun watchlistDao(): WatchlistDao
}