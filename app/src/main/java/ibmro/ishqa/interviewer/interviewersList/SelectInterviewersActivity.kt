package ibmro.ishqa.interviewer.interviewersList

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import ibmro.common.extension.clickOn
import ibmro.common.extension.setInvisible
import ibmro.common.extension.setVisible
import ibmro.common.extension.toast
import ibmro.datanetworksource.repositories.entities.databaseModels.responseModel.InterviewerResponse
import ibmro.ishqa.R
import ibmro.ishqa.base.BaseActivity
import ibmro.ishqa.interview.interviewItem.INTERVIEW_INTERVIEWERS
import ibmro.ishqa.interviewer.interviewerItem.InterviewerActivity
import kotlinx.android.synthetic.main.activity_select_question.*

class SelectInterviewersActivity : BaseActivity<SelectInterviewersContract.View, SelectInterviewersContract.Presenter>(),
        SelectInterviewersContract.View {
    lateinit override var presenter: SelectInterviewersContract.Presenter


    override fun onCreate(savedInstanceState: Bundle?) {
        val checkableList = intent.extras!!.getIntegerArrayList(INTERVIEW_INTERVIEWERS)
                ?: arrayListOf()
        val checkable = intent.extras!!.getBoolean(INTERVIEWERS_CHECKABLE)
        presenter = SelectInterviewersPresenter(this, checkableList, checkable)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_question)

        if (checkable) {
            return_selected_question_btn.clickOn {
                presenter.getInterviewersIdSelected()
            }
        } else {
            return_selected_question_btn.setImageResource(R.drawable.ic_plus)
            return_selected_question_btn.clickOn {
                val intent = Intent(this, InterviewerActivity::class.java)
                intent.putExtra(INTERVIEWER_MODEL, "")
                startActivity(intent)
            }
        }

    }

    override fun populateInterviewers(interviewers: List<InterviewerResponse>, checkableList: List<Int>, checkable: Boolean) {
        if (interviewers.isEmpty()) {
            select_question_no_items.setVisible()
        } else
            select_question_no_items.setInvisible()

        select_question_recycler.layoutManager = LinearLayoutManager(this)
        select_question_recycler.adapter = SelectInterviewersAdapter(ArrayList(interviewers), checkableList, object : InterviewersItemContract {

            override fun delete(id: Int) {
                presenter.delete(id)
            }

            override fun edit(interviewer: InterviewerResponse) {
                presenter.edit(interviewer)
            }

            override fun view(interviewer: InterviewerResponse) {
                presenter.viewProfile(interviewer)
            }

            override fun check(id: Int, isCheck: Boolean) {
                presenter.addInterviewer(id, isCheck)
            }

        }, checkable)
    }

    override fun loading(load: Boolean) {
        if (load) {
            select_interview_progressBar.setVisible()
        } else
            select_interview_progressBar.setInvisible()
    }

    override fun showToast(msg: String) {
        toast(msg)
    }

    override fun returnIdsList(interviewerIds: ArrayList<Int>) {
        val returnIntent = Intent()
        val bundle = Bundle()
        bundle.putIntegerArrayList(RETURN_INTERVIEWERS_ID, interviewerIds)
        returnIntent.putExtras(bundle)
        setResult(Activity.RESULT_OK, returnIntent)
        finish()
    }
}

const val RETURN_INTERVIEWERS_ID = "RETURN_INTERVIEWERS_ID"
const val INTERVIEWERS_CHECKABLE = "INTERVIEWERS_CHECKABLE"
