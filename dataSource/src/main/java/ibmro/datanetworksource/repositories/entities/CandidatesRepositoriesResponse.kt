package ibmro.datanetworksource.repositories.entities

import com.squareup.moshi.Json

//example
data class CandidatesRepositoriesResponse(
        @Json(name = "return_msg") val returnMsg: String,
        @Json(name = "return_code") val retunrCode: Int,
        val candidates: List<RepositoryCandidateResponse>
)