package ibmro.ishqa.interviewer.interviewerItem

import ibmro.datanetworksource.repositories.entities.databaseModels.requestModel.AddInterviewerRequest
import ibmro.datanetworksource.repositories.entities.databaseModels.requestModel.EditInterviewerRequest
import ibmro.ishqa.base.BasePresenter
import ibmro.ishqa.base.BaseView

object InterviewerContract {

    interface View : BaseView {
        fun showToast(msg: String)

        fun finishActivity()
    }

    interface Presenter : BasePresenter<View> {
        fun addInterviewer(body: AddInterviewerRequest)

        fun editInterviewer(body: EditInterviewerRequest)
    }

}