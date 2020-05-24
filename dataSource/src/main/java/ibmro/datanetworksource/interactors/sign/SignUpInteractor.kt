package ibmro.datanetworksource.interactors.sign

import ibmro.datanetworksource.repositories.ISHApi
import ibmro.datanetworksource.repositories.entities.databaseModels.requestModel.SignUpRequest
import ibmro.datanetworksource.repositories.entities.databaseModels.responseModel.BasicResponse
import io.reactivex.Single


class SignUpInteractor(
        private var ishApi: ISHApi
) : (SignUpRequest) -> (Single<BasicResponse>) {
    override fun invoke(body: SignUpRequest): Single<BasicResponse> =
            ishApi.signUp(body)
}