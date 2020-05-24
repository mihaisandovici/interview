package ibmro.ishqa.interview.interviewItem

import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.gson.Gson
import ibmro.common.extension.clickOn
import ibmro.common.extension.setInvisible
import ibmro.common.extension.setVisible
import ibmro.common.extension.toast
import ibmro.ishqa.R
import ibmro.ishqa.R.id.action_save
import ibmro.ishqa.base.BaseActivity
import ibmro.ishqa.candidate.candidateList.CandidateListActivity
import ibmro.ishqa.candidate.candidateList.CandidateViewModel
import ibmro.ishqa.candidate.candidateList.SELECTED_CANDIDATE
import ibmro.ishqa.interview.interviewItem.interviewersList.InterviewerAdapter
import ibmro.ishqa.interview.interviewItem.interviewersList.InterviewerContract
import ibmro.ishqa.interview.interviewItem.questionList.InterviewQuestionListAdapter
import ibmro.ishqa.interview.interviewsList.INTERVIEW_MODEL
import ibmro.ishqa.interview.interviewsList.INTERVIEW_VIEW_MODE
import ibmro.ishqa.interview.interviewsList.tagsRecycler.TagAdapterFlexBox
import ibmro.ishqa.interview.interviewsList.tagsRecycler.TagListMapper
import ibmro.ishqa.interviewer.interviewersList.INTERVIEWERS_CHECKABLE
import ibmro.ishqa.interviewer.interviewersList.RETURN_INTERVIEWERS_ID
import ibmro.ishqa.interviewer.interviewersList.SelectInterviewersActivity
import ibmro.ishqa.profile.PROFILE_TAGS
import ibmro.ishqa.profile.PROFILE_TAG_RESULT
import ibmro.ishqa.profile.RESULT_LOAD_TAGS
import ibmro.ishqa.selectQuestion.RETURN_QUESTION_SELECTED_IDS
import ibmro.ishqa.selectQuestion.ReturnQuestionModel
import ibmro.ishqa.selectTags.TagActivity
import kotlinx.android.synthetic.main.activity_interview.*
import java.util.*
import kotlin.collections.ArrayList

class InterviewActivity :
        BaseActivity<InterviewContract.View, InterviewContract.Presenter>(),
        InterviewContract.View {

    lateinit override var presenter: InterviewContract.Presenter
    private var viewMode = ViewMode.VIEW
    private var setLatyoutOnce = false
    private var dateSelected = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        val interviewString = intent.getStringExtra(INTERVIEW_MODEL)
        viewMode = ViewMode.getViewMode(intent.getIntExtra(INTERVIEW_VIEW_MODE, 0))

        presenter = InterviewPresenter(this, interviewString, viewMode)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_interview)

        setUpButtons()

        interview_candidate_name.clickOn {
            val candidate = InterviewPersistence.candidateModel
            presenter.showDialogProfile(false,
                    candidate!!.fullname,
                    candidate.email,
                    TagListMapper().map(candidate.prefTehnologies),
                    candidate.phoneNumber,
                    candidate.cv,
                    candidate.tehnicalLevel,
                    null)
        }
    }

    private fun setUpButtons() {
        interview_add_tag.clickOn {
            startAddTagsActivity()
        }

        interview_add_question.clickOn {
            presenter.goAddQuestionActivity()
        }

        set_interview_date.clickOn {
            setInterviewDate()
        }

        interview_add_interviewer.clickOn {
            startAddInterviewers()
        }

        interview_add_candidate.clickOn {
            startAddCandidate()
        }
    }

    override fun onStart() {
        super.onStart()
        if (!setLatyoutOnce) {
            presenter.setLayout()
        }
        setLatyoutOnce = true
    }

    override fun setTitle(title: String) {
        this.title = title
    }

    override fun populateTagList() {
        val flexboyLayoutManager = FlexboxLayoutManager(this).apply {
            flexWrap = FlexWrap.WRAP
            flexDirection = FlexDirection.ROW
            alignItems = AlignItems.STRETCH
        }

        interview_tags.apply {
            layoutManager = flexboyLayoutManager
            adapter = TagAdapterFlexBox(InterviewPersistence.tagsList)
        }
    }

    override fun setLayout(layout: ViewMode) {
        when (layout) {
            ViewMode.ADD -> {
                title = getString(R.string.add_interview)
                interview_add_tag.setVisible()
                interview_add_question.setVisible()
                interview_date.setInvisible()
                set_interview_date.setVisible()
                interview_feedback.setInvisible()
            }
            ViewMode.DUPLICATE -> {
                title = getString(R.string.duplicate_interview)
                interview_add_tag.setVisible()
                interview_add_question.setVisible()
                interview_date.setInvisible()
                set_interview_date.setVisible()
                interview_feedback.setInvisible()
            }
            ViewMode.EDIT -> {
                title = getString(R.string.edit_interview)
                interview_add_tag.setVisible()
                interview_add_question.setVisible()
                interview_date.setVisible()
                set_interview_date.setVisible()
                interview_feedback.setInvisible()
            }
            ViewMode.VIEW -> {
                title = getString(R.string.interview)
                interview_add_candidate.setInvisible()
                interview_add_interviewer.setInvisible()
                interview_add_tag.setInvisible()
                interview_add_question.setInvisible()
                interview_date.setVisible()
                set_interview_date.setInvisible()
            }
        }
    }

    override fun showToast(msg: String) {
        toast(msg)
    }

    override fun populateQuestionList(haveNote: Boolean) {
        interview_question_list.layoutManager = LinearLayoutManager(this)
        interview_question_list.adapter = InterviewQuestionListAdapter(this, InterviewPersistence.quesitonList, haveNote)
        interview_question_list.setHasFixedSize(true)
    }

    override fun setLayoutCandidate() {
        val candidate = InterviewPersistence.candidateModel
        if (candidate != null) {
            interview_candidate_name.setVisible()
            interview_candidate_name.text = candidate.fullname
        } else {
            interview_add_candidate.setVisible()
            interview_add_interviewer.setVisible()
            interview_candidate_name.setInvisible()
        }
        if (InterviewPersistence.feedback.isBlank()) {
            interview_feedback.text = InterviewPersistence.feedback
        }
        interview_date.text = InterviewPersistence.scheduleDate
    }

    private fun startAddTagsActivity() {
        val tagListString = ArrayList(InterviewPersistence.tagsList.map {
            it.toString()
        })

        val intent = Intent(this, TagActivity::class.java)
        val bundle = Bundle()
        bundle.putStringArrayList(PROFILE_TAGS, tagListString)

        intent.putExtras(bundle)

        startActivityForResult(intent, RESULT_LOAD_TAGS)
    }

    private fun startAddInterviewers() {
        val interviewerIds = ArrayList(InterviewPersistence.idInterviewers.map {
            it.id
        })

        val intent = Intent(this, SelectInterviewersActivity::class.java)
        val bundle = Bundle()
        bundle.putIntegerArrayList(INTERVIEW_INTERVIEWERS, interviewerIds)
        intent.putExtras(bundle)
        intent.putExtra(INTERVIEWERS_CHECKABLE, true)
        startActivityForResult(intent, RESULT_INTERVIEWERS)
    }

    private fun startAddCandidate() {
        val intent = Intent(this, CandidateListActivity::class.java)
        intent.putExtra(INTERVIEWERS_CHECKABLE, true)

        startActivityForResult(intent, RESULT_CANDIDATE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            RESULT_LOAD_TAGS -> {
                if (resultCode == Activity.RESULT_OK) {
                    val bundle = data!!.extras
                    val tags = bundle!!.getStringArrayList(PROFILE_TAG_RESULT)
                    presenter.setTagList(TagListMapper().map(tags!!))
                }
            }
            RESULT_LOAD_QUESTIONS -> {
                if (resultCode == Activity.RESULT_OK) {
                    val bundle = data!!.extras
                    val questionsId = bundle!!.getString(RETURN_QUESTION_SELECTED_IDS)
                    presenter.setQuestionList(Gson().fromJson(questionsId, ReturnQuestionModel::class.java))
                }
            }
            RESULT_INTERVIEWERS -> {
                if (resultCode == Activity.RESULT_OK) {
                    val bundle = data!!.extras
                    val interviewersIds = bundle!!.getIntegerArrayList(RETURN_INTERVIEWERS_ID)
                    presenter.setInterviewersList(interviewersIds!!.toList())
                }
            }
            RESULT_CANDIDATE -> {
                if (resultCode == Activity.RESULT_OK) {
                    val bundle = data!!.extras
                    val candidate = bundle!!.getSerializable(SELECTED_CANDIDATE) as CandidateViewModel
                    presenter.setCandidateDetails(ibmro.datanetworksource.repositories.entities.viewModels.CandidateViewModel(
                            candidate.id, candidate.firstname, candidate.lastname, candidate.email, candidate.prefTechnologies,
                            candidate.phoneNr, candidate.candidateCV, "Advance"
                    ))
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.restartQuestionListOfInterview()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (viewMode.isEditable()) {
            menuInflater.inflate(R.menu.interview_editable_menu, menu)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (viewMode.isEditable()) {
            when (item!!.itemId) {
                action_save -> {
                    presenter.sendRequest()
                    return true
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }


    override fun startSelectQuestionActivity(intent: Intent) {
        startActivityForResult(intent, RESULT_LOAD_QUESTIONS)
    }


    private fun setInterviewDate() {

        val pickerDialog = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            dateSelected.set(Calendar.YEAR, year)
            dateSelected.set(Calendar.MONTH, month + 1)
            dateSelected.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            setHourInterview()
        }, dateSelected.get(Calendar.YEAR), dateSelected.get(Calendar.MONTH), dateSelected.get(Calendar.DAY_OF_MONTH))

        pickerDialog.show()
    }

    private fun setHourInterview() {
        val timePickerDialog = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
            dateSelected.set(Calendar.HOUR_OF_DAY, hourOfDay)
            dateSelected.set(Calendar.MINUTE, minute)

            presenter.castDateToString(dateSelected)
            interview_date.text = InterviewPersistence.scheduleDate
            interview_date.setVisible()
        },
                dateSelected.get(Calendar.HOUR_OF_DAY),
                dateSelected.get(Calendar.MINUTE), true)

        timePickerDialog.show()
    }

    override fun showErrorDialog(errorList: List<String>) {
        if (errorList.isNotEmpty()) {
            var errorString = ""
            errorList.map {
                errorString = "$errorString \n $it"
            }

            val builder = AlertDialog.Builder(this)
                    .setMessage(errorString)
                    .setTitle(getString(R.string.interiew_is_not_complete))
                    .setIcon(R.drawable.ic_alert)
                    .setPositiveButton(android.R.string.ok) { dialog, which ->
                        dialog.dismiss()
                    }
            builder.show()
        }
    }

    override fun populateInterviewersList() {
        val flexboyLayoutManager = FlexboxLayoutManager(this).apply {
            flexWrap = FlexWrap.WRAP
            flexDirection = FlexDirection.ROW
            alignItems = AlignItems.STRETCH
        }

        interview_interviewer_name.apply {
            layoutManager = flexboyLayoutManager
            adapter = InterviewerAdapter(InterviewPersistence.idInterviewers, object : InterviewerContract {
                override fun showProfile(it: InterviewersModel) {
                    presenter.showDialogProfile(
                            true,
                            it.fullName,
                            it.email,
                            it.tags,
                            null,
                            null,
                            null,
                            it.note
                    )
                }
            })
        }
    }

    override fun finishActivity() {
        finish()
    }

    override fun setNote(note: Int, feedback: String) {
        val string = StringBuilder("")
        if (note != 0) {
            string.append("Note: $note             ")
        }
        if (feedback.isNotBlank()) {
            string.append("Feedback: $feedback")
        }

        if (string.isNotBlank()) {
            interview_feedback.setVisible()
            interview_feedback.text = string
        } else
            interview_feedback.setVisible()
    }
}

const val INTERVIEW_TAG_LIST = "INTERVIEW_TAG_LIST"
const val INTERVIEW_INTERVIEWERS = "INTERVIEW_INTERVIEWERS"
const val RESULT_LOAD_QUESTIONS = 3
const val RESULT_INTERVIEWERS = 4
const val RESULT_CANDIDATE = 5