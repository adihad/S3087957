package uk.ac.tees.mad.aninfo.models

import com.google.gson.annotations.SerializedName

data class JikanResponse(
    @SerializedName("data") val data: List<Anime>
)

data class Anime(
    val mal_id: Int,
    val title: String,
    val images: AnimeImages,
    val synopsis: String
)

data class AnimeImages(
    @SerializedName("jpg") val jpg: ImageDetails
)

data class ImageDetails(
    val image_url: String
)
