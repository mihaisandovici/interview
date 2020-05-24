package ibmro.ishqa.candidate.candidateItem

import ibmro.ishqa.base.BasePresenter
import ibmro.ishqa.base.BaseView

object CandidateContract {

    interface View : BaseView {

        fun showToast(text: String)

        fun replaceCandidateContent()

        fun setLayoutMode(stringResource: Int)
       fun setCandidate(firstName : String?, lastName : String?, phoneNr : String?,
                      email : String?, technicalLevel : String?, prefTechnologies : List<String>,
                       tags : List<String>, cv : String?)

        fun back()

        fun showErrorDialog(errorList: List<String>)

        fun setPrefTechnologiesLayout()

        fun setTags(editable : Boolean)

    }

    interface Presenter : BasePresenter<View> {

        fun save()

        fun setLayoutMode(setLayoutOnce: Boolean)

        fun setTags(tags : ArrayList<String>)

        fun setPrefTech(tags : ArrayList<String>)

        fun deleteCandidate()

        fun loadCandidateById(setLayoutOnce: Boolean)


    }
}