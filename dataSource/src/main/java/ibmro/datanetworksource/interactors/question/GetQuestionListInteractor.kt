package ibmro.datanetworksource.interactors.question

import ibmro.datanetworksource.repositories.ISHApi
import ibmro.datanetworksource.repositories.entities.databaseModels.requestModel.GetQuestionsByTagRequest
import ibmro.datanetworksource.repositories.entities.databaseModels.responseModel.GetQuestionsByTagResponse
import io.reactivex.Single

class GetQuestionListInteractor
constructor(
        private val ishApi: ISHApi
) : (GetQuestionsByTagRequest) -> Single<GetQuestionsByTagResponse> {
    override fun invoke(request: GetQuestionsByTagRequest): Single<GetQuestionsByTagResponse> =
            ishApi.getQuestionsBYTag(request)


}