package ibmro.ishqa.question.questionItem

import ibmro.datanetworksource.repositories.entities.Tag


data class AnswerModel(
        var answerContent: String,
        var correct: Boolean
)

object QuestionPersistent {

    var questionContent: String = ""
    var tagList: List<Tag?> = arrayListOf()
    var answerList: ArrayList<AnswerModel> = arrayListOf(
            AnswerModel("", false)
    )


}

