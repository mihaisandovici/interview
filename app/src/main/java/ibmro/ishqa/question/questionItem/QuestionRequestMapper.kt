package ibmro.ishqa.question.questionItem

import ibmro.common.ModelMapper
import ibmro.datanetworksource.repositories.entities.databaseModels.requestModel.QuestionRequest

class QuestionRequestMapper() :
        ModelMapper<QuestionPersistent, QuestionRequest> {
    override fun map(input: QuestionPersistent): QuestionRequest {
        val acceptList: ArrayList<String> = arrayListOf()
        val suggestedList: ArrayList<String> = arrayListOf()

        input.answerList.map {
            if (it.correct) {
                acceptList.add(it.answerContent)
            } else
                suggestedList.add(it.answerContent)

        }

        return QuestionRequest(
                tags = input.tagList.map {
                    it.toString()
                },
                questionContent = input.questionContent,
                acceptedAnswer = acceptList.toList(),
                suggestedAnswer = suggestedList.toList(),
                createdBy = 1,
                modifiedBy = 1
        )
    }
}
