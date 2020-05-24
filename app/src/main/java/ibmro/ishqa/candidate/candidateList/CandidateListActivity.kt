package ibmro.ishqa.candidate.candidateList

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import ibmro.common.androidCommon.KEY_CANDIDATE_VIEW
import ibmro.common.extension.clickOn
import ibmro.common.extension.toast
import ibmro.datanetworksource.repositories.entities.databaseModels.responseModel.CandidateResponse
import ibmro.ishqa.R
import ibmro.ishqa.base.BaseActivity
import ibmro.ishqa.candidate.candidateItem.CandidateActivity
import ibmro.ishqa.interviewer.interviewersList.INTERVIEWERS_CHECKABLE
import kotlinx.android.synthetic.main.activity_candidate_list.*


class CandidateListActivity : BaseActivity<CandidateListContract.View,
        CandidateListContract.Presenter>(),
        CandidateListContract.View  {

    override lateinit var presenter: CandidateListContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        val checkable= intent.getBooleanExtra(INTERVIEWERS_CHECKABLE,false)
        presenter=CandidateListPresenter(this,checkable)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_candidate_list)
        candidateList_progressBar.visibility = View.GONE
        error_tv_candidates.visibility = View.GONE
        fab_add_candidate.clickOn {
            startActivity( Intent(this, CandidateActivity::class.java).putExtra(KEY_CANDIDATE_VIEW,true))
        }
        //presenter.loadCandidateList()
    }

    override fun setTitle() {
        title=getString(R.string.list_of_candidates)
    }
    override fun replaceCandidateList(candidates: List<CandidateResponse>,checkable : Boolean) {
        if(candidates.isEmpty())
            no_candidate_items.visibility= View.VISIBLE
        else no_candidate_items.visibility=View.GONE

        candidate_list.layoutManager = LinearLayoutManager(this)

        candidate_list.adapter = CandidateListAdapter(ArrayList(candidates), object : CandidateDeleteContract{
            override fun selectCandidate(candidateViewModel: CandidateViewModel) {
                val returnIntent=Intent()
                val bundle =  Bundle()
                bundle.putSerializable(SELECTED_CANDIDATE, candidateViewModel)
                returnIntent.putExtras(bundle)
                setResult(Activity.RESULT_OK, returnIntent)
                finish()
            }

            override fun delete(id: Int) {
               presenter.deleteCandidate(id)
            }
        },checkable)/*, imageCandidate)*/
        candidate_list.setHasFixedSize(true)

    }



    override fun progressbarVisibility(visibility: Boolean) {
        if (visibility) {
            candidateList_progressBar.visibility = View.VISIBLE
        } else
            candidateList_progressBar.visibility = View.GONE
    }

    override fun errorMessageVisibility(visibility: Boolean) {
        if (visibility) {
            error_tv_candidates.visibility = View.VISIBLE
        } else
            error_tv_candidates.visibility = View.GONE
    }

    override fun showToast(message: String) {
        toast(message)
    }

}
