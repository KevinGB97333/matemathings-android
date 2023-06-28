package es.upm.tfg.matemathings.network.models

import com.squareup.moshi.Json

data class VideosResponse(
    @Json(name = "videos")val videoIds: List<Video>
)
data class Video(
    @Json(name = "id")val id : String
)