package ibmro.datanetworksource.interactors.question

import ibmro.datanetworksource.repositories.ISHApi
import ibmro.datanetworksource.repositories.entities.databaseModels.requestModel.UpdateQuestionRequest
import ibmro.datanetworksource.repositories.entities.databaseModels.responseModel.BasicResponse
import io.reactivex.Single

class UpdateQuestionInteractor
constructor(
        private val ishApi: ISHApi
) : (UpdateQuestionRequest) -> Single<BasicResponse> {
    override fun invoke(question: UpdateQuestionRequest): Single<BasicResponse> =
            ishApi.updateQuestion(question)
}