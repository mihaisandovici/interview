package ibmro.datanetworksource.interactors.question

import ibmro.datanetworksource.repositories.ISHApi
import ibmro.datanetworksource.repositories.entities.databaseModels.requestModel.QuestionRequest
import ibmro.datanetworksource.repositories.entities.databaseModels.responseModel.BasicResponse
import io.reactivex.Single

class AddQuestionInteractor
constructor(
        private val ishApi: ISHApi
) : (QuestionRequest) -> Single<BasicResponse> {
    override fun invoke(question: QuestionRequest): Single<BasicResponse> =
            ishApi.addQuestion(question)

}