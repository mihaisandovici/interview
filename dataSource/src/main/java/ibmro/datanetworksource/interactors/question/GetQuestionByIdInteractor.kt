package ibmro.datanetworksource.interactors.question

import ibmro.datanetworksource.repositories.ISHApi
import ibmro.datanetworksource.repositories.entities.databaseModels.requestModel.GetQuestionByIdRequest
import ibmro.datanetworksource.repositories.entities.databaseModels.responseModel.QuestionResponse
import io.reactivex.Single

class GetQuestionByIdInteractor
constructor(
        private val ishApi: ISHApi) : (Int) -> Single<QuestionResponse> {
    override fun invoke(id: Int): Single<QuestionResponse> =
            ishApi.getQuestionsById(GetQuestionByIdRequest(listOf(id), "INTERVIEWER"))
                    .map {
                        it.firstOrNull()
                    }
}