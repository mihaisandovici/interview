package ibmro.datanetworksource.repositories.entities.databaseModels.requestModel

data class GetInterviewsRequest(
        val grade: String?,
        val percentage: Float?,
        val candidateId: Int?,
        val role: String
)