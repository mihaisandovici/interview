package ibmro.datanetworksource.services.services

import ibmro.datanetworksource.repositories.ISHApi
import ibmro.datanetworksource.services.mappers.RepositoryListMapper
import ibmro.datanetworksource.services.models.Candidate
import io.reactivex.Single
import javax.inject.Inject
/*
class GetCandidatesRepositories
@Inject constructor(
        private val ishApi: ISHApi
) : (String, String) -> Single<List<Candidate>> {

    override fun invoke(filterBy: String, search: String): Single<List<Candidate>> =
            ishApi.getAllCandidates("$filterBy:$search")
                    .map {
                        RepositoryListMapper().map(it)
                    }
}
*/
// call uri la server DATA directe




