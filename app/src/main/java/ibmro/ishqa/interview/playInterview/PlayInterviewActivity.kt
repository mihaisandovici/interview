package ibmro.ishqa.interview.playInterview

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.SeekBar
import ibmro.common.extension.clickOn
import ibmro.common.extension.setInvisible
import ibmro.common.extension.setVisible
import ibmro.common.extension.toast
import ibmro.datanetworksource.repositories.entities.databaseModels.responseModel.QuestionResponse
import ibmro.ishqa.R
import ibmro.ishqa.base.BaseActivity
import ibmro.ishqa.interview.interviewsList.INTERVIEW_MODEL
import ibmro.ishqa.question.questionItem.AnswerModel
import ibmro.ishqa.question.questionItem.view.AnswerListAdapterUneditable
import kotlinx.android.synthetic.main.activity_play_interview.*

class PlayInterviewActivity : BaseActivity<PlayInterviewContract.View, PlayInterviewContract.Presenter>(),
        PlayInterviewContract.View {

    lateinit override var presenter: PlayInterviewContract.Presenter

    private var result = ResultEnum.STRONG_DISAGREE

    override fun onCreate(savedInstanceState: Bundle?) {
        val interviewString = intent.getStringExtra(INTERVIEW_MODEL)
        presenter = PlayInterviewPresenter(this, interviewString)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_interview)

        setButtons()
    }

    private fun setButtons() {
        next_question.clickOn {
            presenter.setAnswer(result.ordinal)
            presenter.getQuestion()
            seekBar.progress = 0
            result = ResultEnum.STRONG_DISAGREE
        }

        done_play_interview.clickOn {
            val feedback = feedback_play.text.toString()
            presenter.done(feedback)
        }

        resultWatcher()
    }

    private fun resultWatcher() {
        result_to_string.text = ResultEnum.STRONG_DISAGREE.toString(this)
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                result = ResultEnum.getResultForSeekBar(progress + 1)
                result_to_string.text = result.toString(this@PlayInterviewActivity)
                result_to_string.setTextColor(result.getColor(this@PlayInterviewActivity))
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })
    }

    override fun setQuestion(question: QuestionResponse) {
        question_content_uneditable_play.text = question.questionContent
        val answers: ArrayList<AnswerModel> = arrayListOf()

        question.acceptedAnswer.map {
            answers.add(AnswerModel(it, true))
        }
        question.suggestedAnswer.map {
            answers.add(AnswerModel(it, false))
        }
        populateAnswers(answers)
    }

    private fun populateAnswers(answers: ArrayList<AnswerModel>) {
        answers_list_recycler_play.layoutManager = LinearLayoutManager(this)
        answers_list_recycler_play.adapter = AnswerListAdapterUneditable(this, answers)
        answers_list_recycler_play.setHasFixedSize(true)
    }

    override fun showToast(msg: String) {
        toast(msg)
    }

    override fun loading(load: Boolean) {
        if (load)
            play_progress_barr.setVisible()
        else
            play_progress_barr.setInvisible()
    }

    override fun changeLayout() {
        question_layout_play.setInvisible()
        feedback_layout.setVisible()
    }

    override fun finishActivity() {
        finish()
    }
}
