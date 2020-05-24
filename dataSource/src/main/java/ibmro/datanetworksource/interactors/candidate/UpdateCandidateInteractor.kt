package ibmro.datanetworksource.interactors.candidate

import ibmro.datanetworksource.repositories.ISHApi
import ibmro.datanetworksource.repositories.entities.databaseModels.requestModel.UpdateCandidateRequest
import ibmro.datanetworksource.repositories.entities.databaseModels.responseModel.BasicResponse
import io.reactivex.Single

class UpdateCandidateInteractor
constructor(
        private val ishApi: ISHApi)
    : (UpdateCandidateRequest) -> Single<BasicResponse> {
    override fun invoke(candidate: UpdateCandidateRequest): Single<BasicResponse> =
            ishApi.updateCandidate(candidate)
}
