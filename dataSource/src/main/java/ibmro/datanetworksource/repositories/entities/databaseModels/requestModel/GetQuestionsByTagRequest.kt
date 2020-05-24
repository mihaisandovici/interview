package ibmro.datanetworksource.repositories.entities.databaseModels.requestModel

data class GetQuestionsByTagRequest(
        val tags: List<String>,
        val role: String,
        val oneTagEnough: Boolean
)