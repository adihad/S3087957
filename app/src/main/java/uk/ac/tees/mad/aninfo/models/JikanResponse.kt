package uk.ac.tees.mad.aninfo.models

import com.google.gson.annotations.SerializedName

data class JikanResponse(
    @SerializedName("data") val data: List<Anime>
)

data class JikanAnimeResponse(
    @SerializedName("data") val data: Anime
)

data class Anime(
    val mal_id: Int,
    val title: String,
    val synopsis: String,
    val score: Double?,
    val episodes: Int?,
    val images: AnimeImages,
    val airing: Boolean,
    val url: String,
    val genres: List<Genre>
)

data class Genre(
    val name: String
)

data class AnimeImages(
    @SerializedName("jpg") val jpg: ImageDetails
)

data class ImageDetails(
    val image_url: String
)
