package ibmro.datanetworksource.interactors.sign

import ibmro.datanetworksource.repositories.ISHApi
import ibmro.datanetworksource.repositories.entities.databaseModels.responseModel.LoginResponse
import io.reactivex.Single

class LoginInteractor(
        private var ishApi: ISHApi
) : (String, String) -> (Single<LoginResponse>) {
    override fun invoke(username: String, password: String): Single<LoginResponse> =
            ishApi.login(username, password)
}