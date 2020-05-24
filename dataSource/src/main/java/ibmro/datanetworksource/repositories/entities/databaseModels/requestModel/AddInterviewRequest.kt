package ibmro.datanetworksource.repositories.entities.databaseModels.requestModel

data class AddInterviewRequest(
        val candidateId: String,
        val interviewersIds: List<Int>,
        val test: List<TestInterview>,
        val feedback: String,
        val tags: List<String>,
        val scheduledDateTime: String,
        val role: String
)

data class TestInterview(
        val questionId: Int,
        val answer: Int
)