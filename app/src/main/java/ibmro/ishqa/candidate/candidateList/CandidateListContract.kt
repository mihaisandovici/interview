package ibmro.ishqa.candidate.candidateList

import ibmro.datanetworksource.repositories.entities.databaseModels.responseModel.CandidateResponse
import ibmro.ishqa.base.BasePresenter
import ibmro.ishqa.base.BaseView

object CandidateListContract {

    interface View: BaseView{

        fun replaceCandidateList(candidates: List<CandidateResponse>,checkable : Boolean)

        fun progressbarVisibility(visibility: Boolean)

        fun errorMessageVisibility(visibility: Boolean)

        fun showToast(message: String)

        fun setTitle()

        //fun setCandidateCount(count: Int)
    }

    interface Presenter : BasePresenter<View>{

        fun loadCandidateList()

        fun deleteCandidate(candidateId : Int)
    }
}