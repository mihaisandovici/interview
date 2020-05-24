package ibmro.datanetworksource.interactors.candidate

import ibmro.datanetworksource.repositories.ISHApi
import ibmro.datanetworksource.repositories.entities.databaseModels.responseModel.GetCandidateByIdResponse
import io.reactivex.Single

class GetCandidateByIdInteractor(
        private var ishApi: ISHApi
) : (Int, String) -> (Single<GetCandidateByIdResponse>) {
    override fun invoke(candidateId: Int, role: String): Single<GetCandidateByIdResponse> =
            ishApi.getCandidateById(candidateId, role)
}