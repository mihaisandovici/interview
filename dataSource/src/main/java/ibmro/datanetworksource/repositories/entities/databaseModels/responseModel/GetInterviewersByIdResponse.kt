package ibmro.datanetworksource.repositories.entities.databaseModels.responseModel

data class GetInterviewersByIdResponse(
        val return_msg: String,
        val return_code: Int,
        val interviewers: List<InterviewerResponse>
)

data class InterviewerResponse(
        val id: Int,
        val tags: List<String>,
        val lastName: String,
        val email: String,
        val available: Int,
        val firstName: String,
        val note: String
)