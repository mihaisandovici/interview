package ibmro.datanetworksource.interactors.interviewer

import ibmro.datanetworksource.repositories.ISHApi
import ibmro.datanetworksource.repositories.entities.databaseModels.requestModel.DeleteRequest
import ibmro.datanetworksource.repositories.entities.databaseModels.responseModel.BasicResponse
import io.reactivex.Single

class DeleteInterviewerInteractor(
        private var ishApi: ISHApi
) : (DeleteRequest) -> (Single<BasicResponse>) {
    override fun invoke(request: DeleteRequest): Single<BasicResponse> =
            ishApi.deleteInterviewer(request)
}