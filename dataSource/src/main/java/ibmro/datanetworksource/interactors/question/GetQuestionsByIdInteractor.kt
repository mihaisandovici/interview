package ibmro.datanetworksource.interactors.question

import ibmro.datanetworksource.repositories.ISHApi
import ibmro.datanetworksource.repositories.entities.databaseModels.requestModel.GetQuestionByIdRequest
import ibmro.datanetworksource.repositories.entities.databaseModels.responseModel.QuestionResponse
import io.reactivex.Single

class GetQuestionsByIdInteractor
constructor(
        private val ishApi: ISHApi) : (List<Int>) -> Single<List<QuestionResponse>> {
    override fun invoke(ids: List<Int>): Single<List<QuestionResponse>> =
            ishApi.getQuestionsById(GetQuestionByIdRequest(ids, "INTERVIEWER"))
}