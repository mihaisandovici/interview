package ibmro.datanetworksource.repositories.entities.databaseModels.requestModel

data class UpdateQuestionRequest(
        val id: String,
        val tags: List<String>?,
        val questionContent: String?,
        val acceptedAnswer: List<String>?,
        val suggestedAnswer: List<String>?,
        val createdBy: String?,
        val modifiedBy: String,
        val contentFile: String?,
        val role: String
)