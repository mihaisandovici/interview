package ibmro.datanetworksource.repositories.entities.databaseModels.requestModel

data class EditInterviewRequest(
        val id: Int,
        val candidateId: Int,
        val interviewersIds: List<Int>,
        val test: List<TestModel>,
        val tags: List<String>,
        val scheduledDateTime: String,
        val feedback: String,
        val role: String
)

data class TestModel(
        val questionId: Int,
        val answer: Int
)