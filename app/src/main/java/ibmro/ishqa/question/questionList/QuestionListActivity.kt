package ibmro.ishqa.question.questionList

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import ibmro.common.extension.clickOn
import ibmro.common.extension.toast
import ibmro.datanetworksource.repositories.entities.databaseModels.responseModel.QuestionResponse
import ibmro.ishqa.R
import ibmro.ishqa.base.BaseActivity
import ibmro.ishqa.dashboard.sectionVM.SectionItemVM
import ibmro.ishqa.dashboard.sectionVM.getImage
import ibmro.ishqa.dashboard.sectionVM.getTitleString
import ibmro.ishqa.question.questionItem.QuestionActivity
import kotlinx.android.synthetic.main.activity_question_list.*

class QuestionListActivity : BaseActivity<QuestionListContract.View,
        QuestionListContract.Presenter>(),
        QuestionListContract.View {

    override lateinit var presenter: QuestionListContract.Presenter
    private var imageQuestion: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        presenter = QuestionListPresenter(this,
                intent.getStringExtra(MAIN_TAG),
                intent.getStringExtra(ITEM_TAG)
        )
        super.onCreate(savedInstanceState)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        setContentView(R.layout.activity_question_list)


        fab_add_question.clickOn {
            startActivity(Intent(this, QuestionActivity::class.java))
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun setTitle(sectionItem: SectionItemVM) {
        this.title = sectionItem.itemMainTag.getTitleString(this) + " >> " + sectionItem.titleItem
        imageQuestion = sectionItem.itemTag.getImage()
    }


    override fun replaceListContent(questions: List<QuestionResponse>) {
        if (questions.isEmpty()) {
            no_question_items.visibility = View.VISIBLE
        } else
            no_question_items.visibility = View.GONE

        question_list.layoutManager = LinearLayoutManager(this)
        question_list.adapter = QuestionListAdapter(ArrayList(questions), object : DeleteContract {
            override fun check(id: Int, isCheck: Boolean) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun delete(id: Int) =
                    presenter.deleteQuestion(id)

        }, imageQuestion, false, null)
        question_list.setHasFixedSize(true)
    }

    override fun progressbarVisibility(visibility: Boolean) {
        if (visibility) {
            questionList_progressBar.visibility = View.VISIBLE
        } else
            questionList_progressBar.visibility = View.GONE
    }

    override fun errorMessageVisibility(visibility: Boolean) {
        if (visibility) {
            error_tv_questions.visibility = View.VISIBLE
        } else
            error_tv_questions.visibility = View.GONE
    }

    override fun showToast(message: String) {
        toast(message)
    }

    override fun setQuestionCount(count: Int) {
        title = " $title : $count ${getString(R.string.questions)}"
    }
}

const val MAIN_TAG = "MAIN_TAG_KEY"
const val ITEM_TAG = "ITEM_TAG_KEY"

