package ibmro.datanetworksource.repositories.entities.databaseModels.responseModel

data class AddInterviewResponse(
        val response: ResponseInterviewId,
        val return_msg: String,
        val return_code: Int
)

data class ResponseInterviewId(
        val interviewId: Int
)