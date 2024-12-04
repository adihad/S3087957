package uk.ac.tees.mad.aninfo.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "anime_table")
data class AnimeEntity(
    @PrimaryKey val mal_id: Int,
    val title: String,
    val synopsis: String,
    val imageUrl: String
)

fun Anime.toEntity(): AnimeEntity {
    return AnimeEntity(
        mal_id = this.mal_id,
        title = this.title,
        synopsis = this.synopsis,
        imageUrl = this.images.jpg.image_url
    )
}
