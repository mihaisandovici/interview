package ibmro.datanetworksource.services.mappers

import ibmro.common.ModelMapper
import ibmro.datanetworksource.repositories.entities.CandidatesRepositoriesResponse
import ibmro.datanetworksource.services.models.Candidate

class RepositoryListMapper
    : ModelMapper<CandidatesRepositoriesResponse, List<Candidate>> {
    override fun map(input: CandidatesRepositoriesResponse): List<Candidate> =
            input.candidates
                    .map {
                        Candidate(
                                firstName = it.firstName,
                                lastName = it.lastName,
                                email = it.email,
                                prefTechnologies = it.prefTechnologies)
                    }
}


