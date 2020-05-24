package ibmro.datanetworksource.repositories.entities.databaseModels.requestModel

data class DeleteCandidateRequest(
        val id : String,
        val role : String = "ADMINISTRATOR"
)