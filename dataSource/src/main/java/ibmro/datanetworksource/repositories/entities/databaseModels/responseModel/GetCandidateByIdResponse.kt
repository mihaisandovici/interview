package ibmro.datanetworksource.repositories.entities.databaseModels.responseModel

import com.squareup.moshi.Json

data class GetCandidateByIdResponse(
        @Json(name = "return_msg") val return_msg: String,
        @Json(name = "return_code") val return_code: Int,
        @Json(name = "candidate") val candidates: CandidateByIdResponse
)

data class CandidateByIdResponse(
        val id: Int,
        @Json(name = "technicalLevel") val technicalLevel: String,
        @Json(name = "firstName") val firstName: String,
        @Json(name = "lastName") val lastName: String,
        @Json(name = "phoneNr") val phoneNr: Long,
        @Json(name = "candidateCV") val candidateCV: String?,
        @Json(name = "prefTechnologies") val prefTechnologies: List<String>,
        @Json(name = "email") val email: String,
        @Json(name = "tags") val tags: List<String>
)


