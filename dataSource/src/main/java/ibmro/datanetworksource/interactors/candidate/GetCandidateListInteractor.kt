package ibmro.datanetworksource.interactors.candidate

import ibmro.datanetworksource.repositories.ISHApi
import ibmro.datanetworksource.repositories.entities.databaseModels.requestModel.GetCandidatesRequest
import ibmro.datanetworksource.repositories.entities.databaseModels.responseModel.GetCandidateResponse
import io.reactivex.Single

class GetCandidateListInteractor
    constructor(
            private val ishApi: ISHApi
    ) : (GetCandidatesRequest) -> Single<GetCandidateResponse>
    {
        override fun invoke(request: GetCandidatesRequest): Single<GetCandidateResponse> =
                ishApi.getAllCandidates(request)
    }