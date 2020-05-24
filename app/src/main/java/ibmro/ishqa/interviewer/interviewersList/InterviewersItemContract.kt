package ibmro.ishqa.interviewer.interviewersList

import ibmro.datanetworksource.repositories.entities.databaseModels.responseModel.InterviewerResponse

interface InterviewersItemContract {

    fun delete(id: Int)

    fun edit(interviewer: InterviewerResponse)

    fun check(id: Int, isCheck: Boolean)

    fun view(interviewer: InterviewerResponse)
}