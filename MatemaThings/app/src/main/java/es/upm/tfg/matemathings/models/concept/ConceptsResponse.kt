package es.upm.tfg.matemathings.models.concept

import com.squareup.moshi.Json

data class ConceptsResponse(
    @Json(name = "concepts")val concepts: List<Concept>
)