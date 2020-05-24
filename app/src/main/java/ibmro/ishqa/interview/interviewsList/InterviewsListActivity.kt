package ibmro.ishqa.interview.interviewsList

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.google.gson.Gson
import ibmro.common.extension.clickOn
import ibmro.common.extension.setInvisible
import ibmro.common.extension.setVisible
import ibmro.common.extension.toast
import ibmro.datanetworksource.repositories.entities.viewModels.InterviewViewModel
import ibmro.ishqa.R
import ibmro.ishqa.base.BaseActivity
import ibmro.ishqa.interview.interviewItem.InterviewActivity
import ibmro.ishqa.interview.interviewItem.ViewMode
import ibmro.ishqa.interview.playInterview.PlayInterviewActivity
import kotlinx.android.synthetic.main.activity_interviews_list.*
import java.util.*

class InterviewsListActivity : BaseActivity<InterviewsListContract.View,
        InterviewsListContract.Presenter>(),
        InterviewsListContract.View {

    override lateinit var presenter: InterviewsListContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        presenter = InterviewsListPresenter(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_interviews_list)

        add_interview.clickOn {
            goInterviewActivity(null, ViewMode.ADD)
        }
    }

    override fun replaceListContent(interviewList: List<InterviewViewModel>) {

        interviews_recycler.layoutManager = LinearLayoutManager(this)
        interviews_recycler.adapter = InterviewsListAdapter(ArrayList(interviewList), object : ItemContract {
            override fun edit(interview: InterviewViewModel) {
                goInterviewActivity(interview, ViewMode.EDIT)
            }

            override fun delete(id: Int) {
                presenter.deleteInterview(id)
            }

            override fun duplicate(interview: InterviewViewModel) {
                interview.candidate = null
                goInterviewActivity(interview, ViewMode.DUPLICATE)

            }

            override fun view(interview: InterviewViewModel) {
                goInterviewActivity(interview, ViewMode.VIEW)
            }

            override fun playInterview(interview: InterviewViewModel) {
                goPlayInterviewActivity(interview)
            }
        })
        interviews_recycler.setHasFixedSize(true)
    }

    override fun setTitle() {
        title = getString(R.string.interviews_list)
    }

    override fun loading(load: Boolean) {
        if (load) {
            interviews_progress.setVisible()
            interviews_recycler.setInvisible()

        } else {
            interviews_progress.setInvisible()
            interviews_recycler.setVisible()
        }
    }

    override fun showToast(msg: String) {
        toast(msg)
    }

    override fun goInterviewActivity(interview: InterviewViewModel?, viewMode: ViewMode) {
        val intent = Intent(this, InterviewActivity::class.java)

        if (interview != null) {
            val gson = Gson()
            val jsonString = gson.toJson(interview)
            intent.putExtra(INTERVIEW_MODEL, jsonString)
        } else intent.putExtra(INTERVIEW_MODEL, "")
//        intent.putExtra(INTERVIEW_EDITABLE, editable)
//        intent.putExtra(INTERVIEW_EDIT_REQUEST, editRequest)
        intent.putExtra(INTERVIEW_VIEW_MODE, viewMode.ordinal)

        startActivity(intent)
    }

    override fun goPlayInterviewActivity(interview: InterviewViewModel?) {
        val intent = Intent(this, PlayInterviewActivity::class.java)

        val gson = Gson()
        val jsonString = gson.toJson(interview)
        intent.putExtra(INTERVIEW_MODEL, jsonString)
        startActivity(intent)
    }

}

const val INTERVIEW_EDITABLE = "INTERVIEW_EDITABLE"
const val INTERVIEW_MODEL = "INTERVIEW_MODEL"
const val INTERVIEW_EDIT_REQUEST = "INTERVIEW_EDIT_REQUEST"

const val INTERVIEW_VIEW_MODE = "INTERVIEW_VEW_MODE"
