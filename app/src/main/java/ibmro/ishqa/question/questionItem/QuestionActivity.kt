package ibmro.ishqa.question.questionItem

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import ibmro.common.androidCommon.KEY_ID_QUESTION
import ibmro.common.androidCommon.KEY_QUESTION_VIEW
import ibmro.common.extension.clickOn
import ibmro.common.extension.stringToEditable
import ibmro.datanetworksource.repositories.entities.Tag
import ibmro.ishqa.R
import ibmro.ishqa.R.id.action_delete
import ibmro.ishqa.R.id.action_save
import ibmro.ishqa.base.BaseActivity
import ibmro.ishqa.profile.PROFILE_TAGS
import ibmro.ishqa.profile.PROFILE_TAG_RESULT
import ibmro.ishqa.profile.RESULT_LOAD_TAGS
import ibmro.ishqa.question.questionItem.QuestionPersistent.answerList
import ibmro.ishqa.question.questionItem.QuestionPersistent.questionContent
import ibmro.ishqa.question.questionItem.edit.AnswerListAdapterEditable
import ibmro.ishqa.question.questionItem.view.AnswerListAdapterUneditable
import ibmro.ishqa.selectTags.TagActivity
import kotlinx.android.synthetic.main.activity_add_question.*

class QuestionActivity : BaseActivity<QuestionContract.View,
        QuestionContract.Presenter>(),
        QuestionContract.View {

    lateinit override var presenter: QuestionContract.Presenter
    private var editMode = false
    private var idQuestion = 0
    private var setLayoutOnce = false


    override fun onCreate(savedInstanceState: Bundle?) {
        idQuestion = intent.getIntExtra(KEY_ID_QUESTION, -1)
        editMode = intent.getBooleanExtra(KEY_QUESTION_VIEW, false)
        presenter = QuestionPresenter(idQuestion, editMode, this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_question)

    }

    override fun onStart() {
        super.onStart()
        presenter.setLayoutMode(setLayoutOnce)
        setLayoutOnce = true
    }


    override fun replaceQuestionContent(editable: Boolean) {
        question_content_editable.text = stringToEditable(questionContent)
        question_content_uneditable.text = stringToEditable(questionContent)

        answers_list_recycler.layoutManager = LinearLayoutManager(this)
        if (editable) {
            answers_list_recycler.adapter = AnswerListAdapterEditable(this)
        } else answers_list_recycler.adapter = AnswerListAdapterUneditable(this, QuestionPersistent.answerList)
        answers_list_recycler.setHasFixedSize(true)

    }

    override fun showToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show()
    }

    override fun setLayoutMode(stringResource: Int, editable: Boolean) {
        setTitle(stringResource)
        if (!editable) {
            add_tag_for_question_btn.visibility = View.GONE
            add_answer_btn.visibility = View.GONE
            question_content_editable.visibility = View.GONE
        } else {
            question_content_uneditable.visibility = View.GONE
            question_content_editable.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    questionContent = s.toString()
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    questionContent = s.toString()
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    questionContent = s.toString()
                }

            })
            add_answer_btn.clickOn {
                answerList.add(AnswerModel("", false))
                replaceQuestionContent(editable)
            }

            add_tag_for_question_btn.clickOn {
                startAddTagsActivity()
            }
        }
    }

    override fun setQuestion(questionContent: String, tagList: ArrayList<Tag?>, answerList: ArrayList<AnswerModel>) {
        QuestionPersistent.questionContent = questionContent
        QuestionPersistent.tagList = tagList
        QuestionPersistent.answerList = answerList
    }

    override fun back() {
        this.onBackPressed()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (editMode || idQuestion == -1) {
            menuInflater.inflate(R.menu.question_editable_menu, menu)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (editMode || idQuestion == -1) {
            when (item!!.itemId) {
                action_delete -> {
                    presenter.deleteQuestion()
                    return true
                }
                action_save -> {
                    presenter.save()
                    return true
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun showErrorDialog(errorList: List<String>) {
        var errorString = ""

        errorList.map {
            errorString = "$errorString \n $it "
        }

        val builder = AlertDialog.Builder(this)
                .setMessage(errorString)
                .setTitle(getString(R.string.question_is_not_complete))
                .setIcon(R.drawable.ic_alert)
                .setPositiveButton(android.R.string.ok) { dialog, which ->
                    dialog.dismiss()
                }
        builder.show()
    }

    override fun setTagsLayout() {
        val tagListOfString = ArrayList(QuestionPersistent.tagList.map {
            it.toString()
        })

        val tags = StringBuffer("")

        tagListOfString.map { tagname ->
            tags.append("#$tagname, ")
        }
        if (tags.length > 2)
            tags.replace(tags.lastIndex - 1, tags.lastIndex, "")

        question_tag_list.text = "TAGS: $tags"
    }

    private fun startAddTagsActivity() {
        val tagListString = ArrayList(QuestionPersistent.tagList.map {
            it.toString()
        })

        val intent = Intent(this, TagActivity::class.java)
        val bundle = Bundle()
        bundle.putStringArrayList(PROFILE_TAGS, tagListString)

        intent.putExtras(bundle)

        startActivityForResult(intent, RESULT_LOAD_TAGS)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RESULT_LOAD_TAGS) {
            if (resultCode == Activity.RESULT_OK) {
                val bundle = data!!.extras
                val tags = bundle!!.getStringArrayList(PROFILE_TAG_RESULT)
                presenter.setTags(tags)
            }
        }
    }
}
