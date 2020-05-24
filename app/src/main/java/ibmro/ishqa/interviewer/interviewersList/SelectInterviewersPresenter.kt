package ibmro.ishqa.interviewer.interviewersList

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.support.v7.widget.AppCompatTextView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import com.google.gson.Gson
import ibmro.common.extension.applySchedulers
import ibmro.common.extension.setInvisible
import ibmro.common.extension.setVisible
import ibmro.datanetworksource.interactors.interviewer.DeleteInterviewerInteractor
import ibmro.datanetworksource.interactors.interviewer.GetAllInterviewersInteractor
import ibmro.datanetworksource.repositories.entities.databaseModels.requestModel.DeleteRequest
import ibmro.datanetworksource.repositories.entities.databaseModels.responseModel.InterviewerResponse
import ibmro.ishqa.App
import ibmro.ishqa.R
import ibmro.ishqa.base.BasePresenterImpl
import ibmro.ishqa.interview.interviewsList.tagsRecycler.TagListMapper
import ibmro.ishqa.interviewer.interviewerItem.InterviewerActivity
import ibmro.ishqa.util.flexBox
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy

class SelectInterviewersPresenter(
        private var context: Context,
        private var returnedList: ArrayList<Int>?,
        private var checkable: Boolean
) : BasePresenterImpl<SelectInterviewersContract.View>(),
        SelectInterviewersContract.Presenter {

    var disposable = CompositeDisposable()

    override fun attachView(view: SelectInterviewersContract.View) {
        super.attachView(view)
        getAllInterviewers()
    }

    override fun getAllInterviewers() {
        if (returnedList == null) {
            returnedList = arrayListOf()
        }

        view?.loading(true)
        disposable.add(GetAllInterviewersInteractor(App.ishApi)
                .invoke()
                .applySchedulers()
                .subscribeBy(
                        onSuccess = {
                            view?.populateInterviewers(it.interviewers, returnedList!!.toList(), checkable)
                            view?.loading(false)
                        },
                        onError = {

                        }))
    }

    override fun addInterviewer(id: Int, isCheck: Boolean) {
        when (isCheck) {
            true -> {
                if (!returnedList!!.contains(id))
                    returnedList!!.add(id)
            }
            false -> {
                returnedList!!.remove(id)
            }
        }
    }


    override fun getInterviewersIdSelected() {
        view?.returnIdsList(returnedList!!)
    }

    override fun viewProfile(interviewer: InterviewerResponse) {
        val builder = AlertDialog.Builder(context)

        val view = LayoutInflater.from(context).inflate(R.layout.dialog_view_profile, null)
        val icon = view.findViewById<ImageView>(R.id.icon)
        val name = view.findViewById<AppCompatTextView>(R.id.name)
        val email = view.findViewById<AppCompatTextView>(R.id.email)
        val phone = view.findViewById<AppCompatTextView>(R.id.phone)
        val phoneLayout = view.findViewById<LinearLayout>(R.id.phone_layout)
        val tehnicalLevelLayout = view.findViewById<LinearLayout>(R.id.tehnical_level_layout)
        val tehnicalLevel = view.findViewById<AppCompatTextView>(R.id.tehnical_level)
        val cv = view.findViewById<AppCompatTextView>(R.id.cv)
        val cvLayout = view.findViewById<LinearLayout>(R.id.cv_layout)
        val note = view.findViewById<AppCompatTextView>(R.id.note)
        val noteLayout = view.findViewById<LinearLayout>(R.id.note_layout)
        val tehnologies = view.findViewById<RecyclerView>(R.id.tehnologies)
        val tehnologiesLayout = view.findViewById<LinearLayout>(R.id.tehnologies_layout)

        cvLayout.setInvisible()
        tehnicalLevelLayout.setInvisible()
        phoneLayout.setInvisible()
        noteLayout.setVisible()
        icon.setImageResource(R.drawable.ic_administrator)

        name.text = "${interviewer.firstName} ${interviewer.lastName}"
        email.text = interviewer.email
        note.text = interviewer.note

        val tehnologiesList = TagListMapper().map(interviewer.tags)
        if (tehnologiesList.isEmpty()) {
            tehnologiesLayout.setInvisible()
        } else
            tehnologies.flexBox(context, tehnologiesList, false)

        builder.setView(view)
        builder.setPositiveButton(android.R.string.ok) { dialog, which ->
            dialog.dismiss()
        }

        builder.show()
    }

    override fun edit(interviewer: InterviewerResponse) {
        val interviewerString = Gson().toJson(interviewer)
        val intent = Intent(context, InterviewerActivity::class.java)
        intent.putExtra(INTERVIEWER_MODEL, interviewerString)
        context.startActivity(intent)
    }

    override fun delete(id: Int) {
        disposable.add(DeleteInterviewerInteractor(App.ishApi)
                .invoke(DeleteRequest(id.toString(), "ADMINISTRATOR"))
                .applySchedulers()
                .subscribeBy(
                        onSuccess = {
                            if (it.return_code == 0) {
                                view?.showToast("Item has been deleted")
                            }
                        },
                        onError = {
                            view?.showToast(context.getString(R.string.errror_ocured))
                            getAllInterviewers()
                        }
                )
        )
    }
}

const val INTERVIEWER_MODEL = "INTERVIEWER_MODEL"