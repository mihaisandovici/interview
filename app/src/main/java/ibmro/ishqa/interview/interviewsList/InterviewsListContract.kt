package ibmro.ishqa.interview.interviewsList

import ibmro.datanetworksource.repositories.entities.viewModels.InterviewViewModel
import ibmro.ishqa.base.BasePresenter
import ibmro.ishqa.base.BaseView
import ibmro.ishqa.interview.interviewItem.ViewMode

object InterviewsListContract {

    interface View : BaseView {

        fun replaceListContent(interviewList: List<InterviewViewModel>)

        fun loading(load: Boolean)

        fun setTitle()

        fun showToast(msg: String)

        fun goInterviewActivity(interview: InterviewViewModel?, viewMode: ViewMode)

        fun goPlayInterviewActivity(interview: InterviewViewModel?)
    }

    interface Presenter : BasePresenter<View> {
        fun loadInterviewsList()

        fun deleteInterview(interviewId: Int)
    }

}