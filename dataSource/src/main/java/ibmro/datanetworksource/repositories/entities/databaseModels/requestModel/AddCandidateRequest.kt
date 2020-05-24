package ibmro.datanetworksource.repositories.entities.databaseModels.requestModel

data class AddCandidateRequest (
        val firstName : String?,
        val lastName : String?,
        val phoneNr : String?,
        val email : String?,
        val technicalLevel : String?,
        val prefTechnologies : List<String>,
        val tags : List<String>,
        val candidateCV : String?,
        val role : String = "ADMINISTRATOR"
)

