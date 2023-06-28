package es.upm.tfg.matemathings.models

import com.squareup.moshi.Json

data class Image(
    @Json(name = "url")val url: String
)