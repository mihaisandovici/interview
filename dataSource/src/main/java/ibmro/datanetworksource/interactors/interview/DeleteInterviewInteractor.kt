package ibmro.datanetworksource.interactors.interview

import ibmro.datanetworksource.repositories.ISHApi
import ibmro.datanetworksource.repositories.entities.databaseModels.requestModel.DeleteInterviewRequest
import ibmro.datanetworksource.repositories.entities.databaseModels.responseModel.BasicResponse
import io.reactivex.Single

class DeleteInterviewInteractor(
        private val ishApi: ISHApi
) : (Int, String) -> (Single<BasicResponse>) {
    override fun invoke(interviewId: Int, role: String): Single<BasicResponse> =
            ishApi.deleteInterview(DeleteInterviewRequest(interviewId, role))
}