package ibmro.datanetworksource.interactors.interviewer

import ibmro.datanetworksource.repositories.ISHApi
import ibmro.datanetworksource.repositories.entities.databaseModels.responseModel.GetInterviewersByIdResponse
import io.reactivex.Single

class GetAllInterviewersInteractor(
        private var ishApi: ISHApi
) : () -> (Single<GetInterviewersByIdResponse>) {
    override fun invoke(): Single<GetInterviewersByIdResponse> =
            ishApi.getAllInterviewers()
}