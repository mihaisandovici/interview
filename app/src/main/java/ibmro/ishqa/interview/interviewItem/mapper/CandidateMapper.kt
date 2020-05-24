package ibmro.ishqa.interview.interviewItem.mapper

import ibmro.common.ModelMapper
import ibmro.datanetworksource.repositories.entities.viewModels.CandidateViewModel
import ibmro.ishqa.interview.interviewItem.CandidateModel

class CandidateMapper : ModelMapper<CandidateViewModel?, CandidateModel> {
    override fun map(input: CandidateViewModel?): CandidateModel =
            CandidateModel(input!!.id, input!!.fullName, input.email, input.phoneNumber.toString(), input.cv, input.tehnicalLevel, input.prefTechnologies)

}