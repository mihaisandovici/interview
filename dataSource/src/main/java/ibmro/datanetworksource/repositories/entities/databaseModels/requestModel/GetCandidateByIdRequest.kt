package ibmro.datanetworksource.repositories.entities.databaseModels.requestModel

data class GetCandidateByIdRequest(
        val id : Int,
        val role: String
)