package ibmro.datanetworksource.interactors.interview

import ibmro.datanetworksource.repositories.ISHApi
import ibmro.datanetworksource.repositories.entities.databaseModels.requestModel.AddInterviewRequest
import ibmro.datanetworksource.repositories.entities.databaseModels.responseModel.AddInterviewResponse
import io.reactivex.Single

class AddInterviewInteractor
constructor(
        private val ishApi: ISHApi
) : (AddInterviewRequest) -> Single<AddInterviewResponse> {
    override fun invoke(request: AddInterviewRequest): Single<AddInterviewResponse> =
            ishApi.addInterview(request)
}