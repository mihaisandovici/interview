package ibmro.datanetworksource.interactors.candidate

import ibmro.datanetworksource.repositories.ISHApi
import ibmro.datanetworksource.repositories.entities.databaseModels.requestModel.DeleteCandidateRequest
import ibmro.datanetworksource.repositories.entities.databaseModels.responseModel.BasicResponse
import io.reactivex.Single

class DeleteCandidateInteractor
constructor(private val ishApi: ISHApi)
    : (DeleteCandidateRequest) -> Single<BasicResponse>{
    override fun invoke(candidate : DeleteCandidateRequest): Single<BasicResponse> =
        ishApi.deleteCandidate(candidate)



}