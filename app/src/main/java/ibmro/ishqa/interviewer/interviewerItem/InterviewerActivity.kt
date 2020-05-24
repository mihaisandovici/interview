package ibmro.ishqa.interviewer.interviewerItem

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.gson.Gson
import ibmro.common.extension.clickOn
import ibmro.common.extension.stringToEditable
import ibmro.common.extension.toast
import ibmro.datanetworksource.repositories.entities.databaseModels.requestModel.AddInterviewerRequest
import ibmro.datanetworksource.repositories.entities.databaseModels.requestModel.EditInterviewerRequest
import ibmro.datanetworksource.repositories.entities.databaseModels.responseModel.InterviewerResponse
import ibmro.ishqa.R
import ibmro.ishqa.R.id.action_save
import ibmro.ishqa.base.BaseActivity
import ibmro.ishqa.interview.interviewsList.tagsRecycler.TagListMapper
import ibmro.ishqa.interviewer.interviewersList.INTERVIEWER_MODEL
import ibmro.ishqa.profile.PROFILE_TAGS
import ibmro.ishqa.profile.PROFILE_TAG_RESULT
import ibmro.ishqa.profile.RESULT_LOAD_TAGS
import ibmro.ishqa.selectTags.TagActivity
import ibmro.ishqa.util.flexBox
import kotlinx.android.synthetic.main.activity_interviewer.*

class InterviewerActivity : BaseActivity<InterviewerContract.View, InterviewerContract.Presenter>(), InterviewerContract.View {

    lateinit override var presenter: InterviewerContract.Presenter
    private var tags = arrayListOf<String>()
    private var interviewerID = -1


    override fun onCreate(savedInstanceState: Bundle?) {
        presenter = InterviewerPresenter(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_interviewer)
        setUp()

        interviewer_add_tag.clickOn {
            startAddTagsActivity()
        }
        setTags()
    }

    private fun setUp() {
        val interviewerString = intent.getStringExtra(INTERVIEWER_MODEL)
        if (interviewerString.isNotBlank()) {
            val interview = Gson().fromJson(interviewerString, InterviewerResponse::class.java)
            setDataFromInterviewer(interview)
            interviewerID = interview.id
        }
    }

    override fun showToast(msg: String) {
        toast(msg)
    }

    private fun setTags() {
        interviewer_tags.flexBox(this, TagListMapper().map(tags), false)
    }

    private fun sentInterviewer() {
        val firstName = interviewer_first_name.text.toString()
        val lastName = interviewer_last_name.text.toString()
        val email = interviewer_email.text.toString()
        val note = interviewer_note.text.toString()
        val available = interviewer_available.isChecked

        if (interviewerID == -1)
            presenter.addInterviewer(AddInterviewerRequest(firstName, lastName, email, available, note, tags.toList(), "ADMINISTRATOR"))
        else
            presenter.editInterviewer(EditInterviewerRequest(
                    interviewerID,
                    tags,
                    lastName,
                    email,
                    available,
                    firstName,
                    note,
                    "ADMINISTRATOR"
            ))
    }

    private fun setDataFromInterviewer(interviewer: InterviewerResponse) {
        interviewer_first_name.text = stringToEditable(interviewer.firstName)
        interviewer_last_name.text = stringToEditable(interviewer.lastName)
        interviewer_email.text = stringToEditable(interviewer.email)
        interviewer_note.text = stringToEditable(interviewer.note)
        interviewer_available.isChecked = interviewer.available == 1
        tags = ArrayList(interviewer.tags)
        setTags()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.interview_editable_menu, menu)
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            RESULT_LOAD_TAGS -> {
                if (resultCode == Activity.RESULT_OK) {
                    val bundle = data!!.extras
                    tags = bundle!!.getStringArrayList(PROFILE_TAG_RESULT) ?: arrayListOf()
                    setTags()
                }
            }
        }
    }

    private fun startAddTagsActivity() {
        val tagListString = tags

        val intent = Intent(this, TagActivity::class.java)
        val bundle = Bundle()
        bundle.putStringArrayList(PROFILE_TAGS, tagListString)

        intent.putExtras(bundle)

        startActivityForResult(intent, RESULT_LOAD_TAGS)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            action_save -> {
                sentInterviewer()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun finishActivity() {
        finish()
    }

}
