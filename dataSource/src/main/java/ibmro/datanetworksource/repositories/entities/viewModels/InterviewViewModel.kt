package ibmro.datanetworksource.repositories.entities.viewModels

import ibmro.datanetworksource.repositories.entities.Tag

data class InterviewViewModel(
        val id: Int,
        val tags: List<Tag?>,
        var feedback: String,
        val procentage: Float,
        var candidate: CandidateViewModel?,
        val date: String,
        val grade: Int,
        val test: List<TestViewModel>,
        val interviewersId: List<Int>
)

data class CandidateViewModel(
        val id: Int,
        val firsName: String,
        val lastName: String,
        val email: String,
        val prefTechnologies: List<String>,
        val phoneNumber: Long,
        val cv: String?,
        val tehnicalLevel: String
) {
    val fullName = "$firsName $lastName"
}

data class TestViewModel(
        var question: Int,
        var questionResult: Int
)
//
//data class QuestionInterviewModel(
//        val questionContent: String,
//        val tags : List<String>,
//        val contentFile : List<String>,
//        val acceptedAnswers : List<String>
//)