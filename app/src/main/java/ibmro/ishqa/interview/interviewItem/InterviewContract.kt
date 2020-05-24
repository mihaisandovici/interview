package ibmro.ishqa.interview.interviewItem

import android.content.Intent
import ibmro.datanetworksource.repositories.entities.Tag
import ibmro.datanetworksource.repositories.entities.viewModels.CandidateViewModel
import ibmro.ishqa.base.BasePresenter
import ibmro.ishqa.base.BaseView
import ibmro.ishqa.selectQuestion.ReturnQuestionModel
import java.util.*

object InterviewContract {

    interface View : BaseView {

        fun setTitle(title: String)

        fun populateTagList()

        fun populateQuestionList(haveNote: Boolean)

        fun showToast(msg: String)

        fun setLayoutCandidate()

        fun startSelectQuestionActivity(intent: Intent)

        fun showErrorDialog(errorList: List<String>)

        fun populateInterviewersList()

        fun finishActivity()

        fun setNote(note: Int, feedback: String)

        fun setLayout(layout: ViewMode)
    }

    interface Presenter : BasePresenter<View> {

        fun setLayout()

        fun restartQuestionListOfInterview()

        fun setTagList(tagList: List<Tag?>)

        fun goAddQuestionActivity()

        fun setQuestionList(questionList: ReturnQuestionModel)

        fun castDateToString(dateSelected: Calendar)

        fun sendRequest()

        fun setInterviewersList(interviewersId: List<Int>)

        fun showDialogProfile(isInterviewer: Boolean, peopleName: String, peopleEmail: String, tagList: List<Tag?>,
                              peoplePhone: String?, peopleCv: String?, peopleTenicalLvl: String?, peopleNote: String?)

        fun setCandidateDetails(candidate: CandidateViewModel?)

    }

}