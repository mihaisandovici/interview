package ibmro.datanetworksource.interactors.interviewer

import ibmro.datanetworksource.repositories.ISHApi
import ibmro.datanetworksource.repositories.entities.databaseModels.requestModel.EditInterviewerRequest
import ibmro.datanetworksource.repositories.entities.databaseModels.responseModel.BasicResponse
import io.reactivex.Single

class UpdateInterviewerInteractor(
        private var ishApi: ISHApi
) : (EditInterviewerRequest) -> (Single<BasicResponse>) {
    override fun invoke(body: EditInterviewerRequest): Single<BasicResponse> =
            ishApi.editInterviewer(body)
}