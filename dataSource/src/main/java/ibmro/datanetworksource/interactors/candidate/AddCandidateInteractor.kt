package ibmro.datanetworksource.interactors.candidate

import ibmro.datanetworksource.repositories.ISHApi
import ibmro.datanetworksource.repositories.entities.databaseModels.requestModel.AddCandidateRequest
import ibmro.datanetworksource.repositories.entities.databaseModels.responseModel.BasicResponse
import io.reactivex.Single

class AddCandidateInteractor
constructor(
        private val ishApi: ISHApi)
    : (AddCandidateRequest) -> Single<BasicResponse> {
    override fun invoke(candidate: AddCandidateRequest): Single<BasicResponse> =
        ishApi.addCandidate(candidate)
}