package ibmro.datanetworksource.interactors.sections

import ibmro.datanetworksource.repositories.LearnApi
import ibmro.datanetworksource.repositories.entities.databaseModels.responseModel.LearnItemResponse
import io.reactivex.Single

class GetLearnItemsInteraction(
        private var learnApi: LearnApi
) : () -> (Single<LearnItemResponse>) {
    override fun invoke(): Single<LearnItemResponse> =
            learnApi.getAllArticles()
}