package ibmro.ishqa.candidate.candidateItem


import ibmro.common.ModelMapper
import ibmro.datanetworksource.repositories.entities.databaseModels.requestModel.AddCandidateRequest

class CandidateRequestMapper() :
        ModelMapper<CandidatePersistent, AddCandidateRequest> {
    override fun map(input: CandidatePersistent): AddCandidateRequest {

        //val phone= input.phoneNr
        return AddCandidateRequest(
                firstName= input.firstName,
                lastName =input.lastName,
               // phoneNr = "$phone", //"$0phone",
                phoneNr = input.phoneNr,
                email =input.email,
                technicalLevel =input.technicalLevel,
                prefTechnologies =input.prefTechnologies,
                tags = input.tags,
                candidateCV = input.candidateCV
        )
    }
}
