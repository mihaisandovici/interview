package ibmro.ishqa.selectQuestion

import ibmro.datanetworksource.repositories.entities.databaseModels.responseModel.QuestionResponse
import ibmro.ishqa.base.BasePresenter
import ibmro.ishqa.base.BaseView

object SelectQuestionContract {

    interface View : BaseView {
        fun populateQuestionList(questionList: ArrayList<QuestionResponse>, imageTag: Int, checkableList: List<Int>)

        fun returnQuestionSelected(questions: ReturnQuestionModel)

        fun showToast(msg: String)

        fun loading(load: Boolean)
    }

    interface Presenter : BasePresenter<View> {
        fun loadQuestions()

        fun addQuestionId(id: Int, isCheckd: Boolean)

        fun getQuestionSelected()
    }
}