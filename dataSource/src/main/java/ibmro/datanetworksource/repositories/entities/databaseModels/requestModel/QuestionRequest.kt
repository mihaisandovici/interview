package ibmro.datanetworksource.repositories.entities.databaseModels.requestModel

data class QuestionRequest(
        val tags: List<String>,
        val questionContent: String,
        val acceptedAnswer: List<String>,
        val suggestedAnswer: List<String>,
        val createdBy: Int,
        val modifiedBy: Int,
        val contentFile: String = "",
        val keywords: List<String> = listOf(),
        val role: String = "INTERVIEWER"
) {
}