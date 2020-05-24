package ibmro.ishqa.question.questionItem

import ibmro.datanetworksource.repositories.entities.Tag
import ibmro.ishqa.base.BasePresenter
import ibmro.ishqa.base.BaseView

object QuestionContract {

    interface View : BaseView {

        fun showToast(text: String)

        fun replaceQuestionContent(editable: Boolean)

        fun setLayoutMode(stringResource: Int, editable: Boolean)

        fun setQuestion(questionContent: String, tagList: ArrayList<Tag?>, answerList: ArrayList<AnswerModel>)

        fun back()

        fun showErrorDialog(errorList: List<String>)

        fun setTagsLayout()
    }

    interface Presenter : BasePresenter<View> {

        fun save()

        fun setLayoutMode(setLayoutOnce: Boolean)

        fun deleteQuestion()

        fun setTags(tagList: ArrayList<String>?)

    }
}