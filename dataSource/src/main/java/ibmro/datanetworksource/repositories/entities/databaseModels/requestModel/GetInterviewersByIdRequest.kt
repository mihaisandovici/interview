package ibmro.datanetworksource.repositories.entities.databaseModels.requestModel

class GetInterviewersByIdRequest(
        val ids: List<Int>,
        val role: String
) {
}