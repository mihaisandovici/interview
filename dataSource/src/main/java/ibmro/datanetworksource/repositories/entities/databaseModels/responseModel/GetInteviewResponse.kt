package ibmro.datanetworksource.repositories.entities.databaseModels.responseModel

data class GetInteviewResponse(
        val return_msg: String,
        val interviews: List<InterviewResponse>,
        val return_code: Int
)

data class InterviewResponse(
        val tags: List<String>,
        val feedback: String,
        val id: Int,
        val candidateId: Int,
        val percentage: Float,
        val test: List<TestResponse>,
        val interviewersIds: List<Int>,
        val grade: Int,
        val scheduledDateTime: String

)

data class TestResponse(
        val questionId: Int,
        val answer: Int
)