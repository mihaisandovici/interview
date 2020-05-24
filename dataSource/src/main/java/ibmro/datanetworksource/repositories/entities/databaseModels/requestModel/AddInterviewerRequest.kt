package ibmro.datanetworksource.repositories.entities.databaseModels.requestModel

data class AddInterviewerRequest(
        val firstName: String,
        val lastName: String,
        val email: String,
        val available: Boolean,
        val note: String,
        val tags: List<String>,
        val role: String
) {
}