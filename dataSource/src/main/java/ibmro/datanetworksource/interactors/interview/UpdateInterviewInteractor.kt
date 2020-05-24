package ibmro.datanetworksource.interactors.interview

import ibmro.datanetworksource.repositories.ISHApi
import ibmro.datanetworksource.repositories.entities.databaseModels.requestModel.EditInterviewRequest
import ibmro.datanetworksource.repositories.entities.databaseModels.responseModel.BasicResponse
import io.reactivex.Single

class UpdateInterviewInteractor(
        private var ishApi: ISHApi
) : (EditInterviewRequest) -> (Single<BasicResponse>) {
    override fun invoke(p1: EditInterviewRequest): Single<BasicResponse> =
            ishApi.updateInterview(p1)
}