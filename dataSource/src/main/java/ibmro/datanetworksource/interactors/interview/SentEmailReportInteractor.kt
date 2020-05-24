package ibmro.datanetworksource.interactors.interview

import ibmro.datanetworksource.repositories.ISHApi
import ibmro.datanetworksource.repositories.entities.databaseModels.responseModel.SendEmailResponse
import io.reactivex.Single

class SentEmailReportInteractor(
        private var ishApi: ISHApi
) : (Int, String) -> (Single<SendEmailResponse>) {
    override fun invoke(interviewerId: Int, role: String): Single<SendEmailResponse> =
            ishApi.senEmailReport(interviewerId, role)
}