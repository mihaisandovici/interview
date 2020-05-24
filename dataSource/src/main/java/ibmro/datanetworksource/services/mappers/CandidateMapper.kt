package ibmro.datanetworksource.services.mappers

import ibmro.common.ModelMapper
import ibmro.datanetworksource.repositories.entities.databaseModels.responseModel.CandidateByIdResponse
import ibmro.datanetworksource.repositories.entities.viewModels.CandidateViewModel

class CandidateMapper : ModelMapper<CandidateByIdResponse, CandidateViewModel> {
    override fun map(input: CandidateByIdResponse): CandidateViewModel =
            CandidateViewModel(
                    id = input.id,
                    email = input.email,
                    lastName = input.lastName,
                    prefTechnologies = input.prefTechnologies,
                    firsName = input.firstName,
                    phoneNumber = input.phoneNr,
                    tehnicalLevel = input.technicalLevel,
                    cv = input.candidateCV
            )

}