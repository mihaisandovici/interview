package ibmro.datanetworksource.interactors.interviewer

import ibmro.datanetworksource.repositories.ISHApi
import ibmro.datanetworksource.repositories.entities.databaseModels.requestModel.GetInterviewersByIdRequest
import ibmro.datanetworksource.repositories.entities.databaseModels.responseModel.InterviewerResponse
import io.reactivex.Single

class GetInterviewersByIdInteractor(
        private var ishApi: ISHApi
) : (GetInterviewersByIdRequest) -> (Single<List<InterviewerResponse>>) {
    override fun invoke(request: GetInterviewersByIdRequest): Single<List<InterviewerResponse>> =
            ishApi.getInterviewersById(request)
}