package ibmro.ishqa.interview.interviewItem

import ibmro.datanetworksource.repositories.entities.Tag
import java.util.*

object InterviewPersistence {
    var interviewID: Int? = null
    var candidateModel: CandidateModel? = null
    var tagsList: ArrayList<Tag?> = arrayListOf()
    var quesitonList: ArrayList<QuestionModel> = arrayListOf()
    var scheduleDate: String = ""
    var feedback: String = ""
    var idInterviewers: List<InterviewersModel> = listOf()
}

data class QuestionModel(
        var questionId: Int,
        var questionContent: String,
        var answer: Int
)

data class CandidateModel(
        var id: Int,
        var fullname: String,
        var email: String,
        var phoneNumber: String,
        var cv: String?,
        var tehnicalLevel: String,
        var prefTehnologies: List<String>
)

data class InterviewersModel(
        var id: Int,
        var fullName: String,
        var email: String,
        var tags: List<Tag?>,
        var available: Int,
        var note: String
)

