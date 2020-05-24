package ibmro.datanetworksource.interactors.interviewer

import ibmro.datanetworksource.repositories.ISHApi
import ibmro.datanetworksource.repositories.entities.databaseModels.requestModel.AddInterviewerRequest
import ibmro.datanetworksource.repositories.entities.databaseModels.responseModel.BasicResponse
import io.reactivex.Single

class AddInterviewerInteractor(
        private val ishApi: ISHApi
) : (AddInterviewerRequest) -> (Single<BasicResponse>) {
    override fun invoke(request: AddInterviewerRequest): Single<BasicResponse> =
            ishApi.addInterviewer(request)

}