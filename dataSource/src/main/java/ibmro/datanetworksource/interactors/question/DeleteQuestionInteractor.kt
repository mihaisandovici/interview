package ibmro.datanetworksource.interactors.question

import ibmro.datanetworksource.repositories.ISHApi
import ibmro.datanetworksource.repositories.entities.databaseModels.requestModel.DeleteRequest
import ibmro.datanetworksource.repositories.entities.databaseModels.responseModel.BasicResponse
import io.reactivex.Single

class DeleteQuestionInteractor
constructor(private val ishApi: ISHApi
) : (DeleteRequest) -> Single<BasicResponse> {
    override fun invoke(request: DeleteRequest): Single<BasicResponse> =
            ishApi.deleteQuestion(request)
}