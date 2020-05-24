package ibmro.datanetworksource.repositories.entities.databaseModels.requestModel

data class GetQuestionByIdRequest(
        val id: List<Int>,
        val role: String
)