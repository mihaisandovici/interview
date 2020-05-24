package ibmro.ishqa.interviewer.interviewersList

import ibmro.datanetworksource.repositories.entities.databaseModels.responseModel.InterviewerResponse
import ibmro.ishqa.base.BasePresenter
import ibmro.ishqa.base.BaseView

object SelectInterviewersContract {

    interface View : BaseView {
        fun populateInterviewers(interviewers: List<InterviewerResponse>, checkableList: List<Int>, checkable: Boolean)

        fun loading(load: Boolean)

        fun showToast(msg: String)

        fun returnIdsList(interviewerIds: ArrayList<Int>)
    }

    interface Presenter : BasePresenter<View> {

        fun getAllInterviewers()

        fun addInterviewer(id: Int, isCheck: Boolean)

        fun getInterviewersIdSelected()

        fun viewProfile(interviewer: InterviewerResponse)

        fun edit(interviewer: InterviewerResponse)

        fun delete(id: Int)
    }

}