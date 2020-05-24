package ibmro.ishqa.question.questionList

import ibmro.datanetworksource.repositories.entities.databaseModels.responseModel.QuestionResponse
import ibmro.ishqa.base.BasePresenter
import ibmro.ishqa.base.BaseView
import ibmro.ishqa.dashboard.sectionVM.SectionItemVM

object QuestionListContract {

    interface View : BaseView {

        fun setTitle(sectionItem: SectionItemVM)

        fun replaceListContent(questions: List<QuestionResponse>)

        fun progressbarVisibility(visibility: Boolean)

        fun errorMessageVisibility(visibility: Boolean)

        fun showToast(message: String)

        fun setQuestionCount(count: Int)

    }

    interface Presenter : BasePresenter<View> {

        fun loadSectionItem()

        fun loadQuestionList()

        fun deleteQuestion(idQuestion: Int)
    }

}