package ibmro.datanetworksource.repositories.entities.databaseModels.requestModel

data class EditInterviewerRequest(
        val id: Int,
        val tags: List<String>,
        val lastName: String,
        val email: String,
        val available: Boolean,
        val firstName: String,
        val note: String,
        val role: String
) {
}