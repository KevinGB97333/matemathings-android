package es.upm.tfg.matemathings.models.concept

import com.squareup.moshi.Json
import es.upm.tfg.matemathings.models.Image

data class ConceptContent(
    @Json(name = "id") val id : Int,
    @Json(name = "title") val title : String,
    @Json(name = "category") val category: String,
    @Json(name = "image") val image: Image,
    @Json(name = "location") val location: String?,
    @Json(name = "authors")val authors: String,
    @Json(name = "description")val description: String
)