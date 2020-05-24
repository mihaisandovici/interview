package ibmro.datanetworksource.repositories.entities.databaseModels.responseModel

data class GetQuestionsByTagResponse(
        val return_msg: String,
        val questions: List<QuestionResponse>
)

data class QuestionResponse(
        val tags: List<String>,
        val id: Int,
        val contentFile: String,
        val createdBy: Int,
        val acceptedAnswer: List<String>,
        val suggestedAnswer: List<String>,
        val modifiedBy: Int,
        val questionContent: String
)