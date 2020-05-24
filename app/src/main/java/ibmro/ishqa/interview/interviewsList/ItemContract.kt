package ibmro.ishqa.interview.interviewsList

import ibmro.datanetworksource.repositories.entities.viewModels.InterviewViewModel

interface ItemContract {

    fun delete(id: Int)

    fun duplicate(interview: InterviewViewModel)

    fun view(interview: InterviewViewModel)

    fun playInterview(interview: InterviewViewModel)

    fun edit(interview: InterviewViewModel)
}