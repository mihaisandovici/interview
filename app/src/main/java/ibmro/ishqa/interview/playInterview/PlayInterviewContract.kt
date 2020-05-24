package ibmro.ishqa.interview.playInterview

import ibmro.datanetworksource.repositories.entities.databaseModels.responseModel.QuestionResponse
import ibmro.ishqa.base.BasePresenter
import ibmro.ishqa.base.BaseView

object PlayInterviewContract {

    interface View : BaseView {

        fun setQuestion(question: QuestionResponse)

        fun showToast(msg: String)

        fun loading(load: Boolean)

        fun changeLayout()

        fun finishActivity()
    }

    interface Presenter : BasePresenter<View> {

        fun getQuestion()

        fun setAnswer(result: Int)

        fun done(feedback: String)

    }

}