package ibmro.datanetworksource.interactors.interview

import ibmro.datanetworksource.repositories.ISHApi
import ibmro.datanetworksource.repositories.entities.databaseModels.requestModel.GetInterviewsRequest
import ibmro.datanetworksource.repositories.entities.databaseModels.responseModel.GetInteviewResponse
import io.reactivex.Single

class GetInterviewsListInteractor(
        private val ishApi: ISHApi
) : (GetInterviewsRequest) -> (Single<GetInteviewResponse>) {
    override fun invoke(request: GetInterviewsRequest): Single<GetInteviewResponse> =
            ishApi.getInterviews(request)
}
