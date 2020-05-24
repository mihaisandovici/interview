package ibmro.datanetworksource.repositories.entities

import com.squareup.moshi.Json

//example
data class RepositoryCandidateResponse(
        val lastName: String,
        val prefTechnologies: String,
        val technicalLevel: String,
        val email: String,
        //@Json(name = "CNP") val cnp: String,
        val phoneNr: Long,
        val firstName: String
)
