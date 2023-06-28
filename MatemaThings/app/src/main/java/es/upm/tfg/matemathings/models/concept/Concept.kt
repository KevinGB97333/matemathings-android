package es.upm.tfg.matemathings.models.concept

import com.squareup.moshi.Json

data class Concept(
    @Json(name = "concept")val concept: ConceptContent
)