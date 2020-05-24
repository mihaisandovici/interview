package ibmro.ishqa.selectQuestion

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.google.gson.Gson
import ibmro.common.extension.clickOn
import ibmro.common.extension.setInvisible
import ibmro.common.extension.setVisible
import ibmro.common.extension.toast
import ibmro.datanetworksource.repositories.entities.databaseModels.responseModel.QuestionResponse
import ibmro.ishqa.R
import ibmro.ishqa.base.BaseActivity
import ibmro.ishqa.interview.interviewItem.InterviewPersistence
import ibmro.ishqa.interview.interviewsList.INTERVIEW_MODEL
import ibmro.ishqa.question.questionList.DeleteContract
import ibmro.ishqa.question.questionList.QuestionListAdapter
import kotlinx.android.synthetic.main.activity_select_question.*

class SelectQuestionActivity : BaseActivity<SelectQuestionContract.View, SelectQuestionContract.Presenter>(),
        SelectQuestionContract.View {

    lateinit override var presenter: SelectQuestionContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        val interviewString = intent.getStringExtra(INTERVIEW_MODEL)
        val interview = Gson().fromJson(interviewString, InterviewPersistence::class.java)
        val idQuestionSelectedList = interview.quesitonList.map { it.questionId }

        presenter = SelectQuestionPresenter(this, interview.tagsList, ArrayList(idQuestionSelectedList))

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_question)

        setUpButtons()

    }

    private fun setUpButtons() {
        return_selected_question_btn.clickOn {
            presenter.getQuestionSelected()
        }
    }

    override fun populateQuestionList(questionList: ArrayList<QuestionResponse>, imageTag: Int, checkableList: List<Int>) {
        if (questionList.isEmpty()) {
            select_question_no_items.setVisible()
        } else
            select_question_no_items.setInvisible()

        select_question_recycler.layoutManager = LinearLayoutManager(this)
        select_question_recycler.adapter = QuestionListAdapter(questionList, object : DeleteContract {
            override fun check(id: Int, isCheck: Boolean) {
                presenter.addQuestionId(id, isCheck)
            }

            override fun delete(id: Int) {

            }
        }, imageTag, true, checkableList)
        select_question_recycler.setHasFixedSize(true)
    }

    override fun showToast(msg: String) {
        toast(msg)
    }

    override fun returnQuestionSelected(questions: ReturnQuestionModel) {
        val returnIntent = Intent()
        val bundle = Bundle()
        val returnQuestionsString = Gson().toJson(questions)
        bundle.putString(RETURN_QUESTION_SELECTED_IDS, returnQuestionsString)
        returnIntent.putExtras(bundle)
        setResult(Activity.RESULT_OK, returnIntent)
        finish()
    }

    override fun loading(load: Boolean) {
        if (load)
            select_interview_progressBar.setVisible()
        else
            select_interview_progressBar.setInvisible()
    }
}

const val RETURN_QUESTION_SELECTED_IDS = "RETURN_QUESTION_SELECTED_IDS"
