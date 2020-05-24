package ibmro.datanetworksource.repositories.entities.databaseModels.requestModel

data class SignUpRequest(
        val firstName: String,
        val lastName: String,
        val username: String,
        val password: String,
        val role: String
)