package ibmro.ishqa.interview.interviewItem

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
import ibmro.datanetworksource.interactors.interview.AddInterviewInteractor
import ibmro.datanetworksource.interactors.interview.UpdateInterviewInteractor
import ibmro.datanetworksource.interactors.interviewer.GetInterviewersByIdInteractor
import ibmro.datanetworksource.interactors.question.GetQuestionByIdInteractor
import ibmro.datanetworksource.repositories.entities.Tag
import ibmro.datanetworksource.repositories.entities.databaseModels.requestModel.*
import ibmro.datanetworksource.repositories.entities.viewModels.CandidateViewModel
import ibmro.datanetworksource.repositories.entities.viewModels.InterviewViewModel
import ibmro.datanetworksource.repositories.entities.viewModels.TestViewModel
import ibmro.ishqa.App
import ibmro.ishqa.R
import ibmro.ishqa.base.BasePresenterImpl
import ibmro.ishqa.interview.interviewItem.mapper.CandidateMapper
import ibmro.ishqa.interview.interviewsList.INTERVIEW_MODEL
import ibmro.ishqa.interview.interviewsList.tagsRecycler.TagListMapper
import ibmro.ishqa.selectQuestion.ReturnQuestionModel
import ibmro.ishqa.selectQuestion.SelectQuestionActivity
import ibmro.ishqa.util.flexBox
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import java.util.*
import kotlin.collections.ArrayList

class InterviewPresenter constructor(
        private var context: Context,
        interviewString: String,
        private var viewMode: ViewMode
) : BasePresenterImpl<InterviewContract.View>()
        , InterviewContract.Presenter {

    private var disposable = CompositeDisposable()

    val gson = Gson()
    val interview: InterviewViewModel?

    init {
        if (interviewString.isNotBlank()) {
            interview = gson.fromJson(interviewString, InterviewViewModel::class.java)
        } else {
            interview = null
        }
    }

    override fun setLayout() {
//        when (interview) {
//            null -> addLayout()
//            else -> if (!editable) viewLayout(interview)
//            else if (editRequest)
//                editLayout(interview)
//            else
//                duplicateLayout(interview)
//        }
        when (viewMode) {
            ViewMode.ADD -> addLayout()
            ViewMode.EDIT -> editLayout(interview!!)
            ViewMode.DUPLICATE -> duplicateLayout(interview!!)
            ViewMode.VIEW -> viewLayout(interview!!)

        }

    }

    private fun viewLayout(interview: InterviewViewModel) {
        view?.setLayout(ViewMode.VIEW)
        setInterview(interview, true, false)
        view?.setNote(interview.grade, interview.feedback)
    }

    private fun duplicateLayout(interview: InterviewViewModel) {
        view?.setLayout(ViewMode.DUPLICATE)
        setInterview(interview, false, true)
    }

    private fun editLayout(interview: InterviewViewModel) {
        view?.setLayout(ViewMode.EDIT)
        setInterview(interview, true, false)
    }

    private fun addLayout() {
        view?.setLayout(ViewMode.ADD)
        setInterview(InterviewViewModel(
                id = -1,
                tags = listOf(),
                feedback = "",
                procentage = 0.0f,
                candidate = null,
                date = "",
                grade = 0,
                test = listOf(),
                interviewersId = listOf()
        ), false, false)
    }

    private fun setInterview(interview: InterviewViewModel, haveNote: Boolean, setNewDate: Boolean) {
        interview.test.map {
            if (setNewDate) {
                it.questionResult = 0
            }
            setTestList(it, haveNote)
        }
        setInterviewersList(interview.interviewersId)
        setCandidateDetails(interview.candidate)
        setTagList(ArrayList(interview.tags))
        if (!setNewDate) {
            InterviewPersistence.scheduleDate = interview.date
        }
        InterviewPersistence.feedback = interview.feedback
        view?.setLayoutCandidate()

    }

    override fun setInterviewersList(interviewersId: List<Int>) {
        if (interviewersId.isNotEmpty())
            disposable.add(GetInterviewersByIdInteractor(App.ishApi)
                    .invoke(GetInterviewersByIdRequest(interviewersId, "ADMINISTRATOR"))
                    .applySchedulers()
                    .subscribeBy(
                            onSuccess = {
                                InterviewPersistence.idInterviewers = it.map { i ->
                                    InterviewersModel(i.id,
                                            "${i.firstName} ${i.lastName}",
                                            i.email,
                                            TagListMapper().map(i.tags),
                                            i.available,
                                            i.note)
                                }
                                view?.populateInterviewersList()
                            },
                            onError = {
                                view?.showToast(it.message!!)
                            }
                    ))
    }

    override fun setQuestionList(questionList: ReturnQuestionModel) {
        InterviewPersistence.quesitonList = ArrayList(questionList.question)
        view?.populateQuestionList(false)
    }

    private fun setTestList(testList: TestViewModel, haveNote: Boolean) {
        disposable.add(GetQuestionByIdInteractor(App.ishApi)
                .invoke(testList.question)
                .applySchedulers()
                .subscribeBy(
                        onSuccess = {
                            InterviewPersistence.quesitonList.add(QuestionModel(testList.question, it.questionContent, testList.questionResult))
                            view?.populateQuestionList(haveNote)
                        },
                        onError = {
                            view?.showToast(context.getString(R.string.errror_ocured))
                        })
        )
    }

    override fun setCandidateDetails(candidate: CandidateViewModel?) {
        if (candidate != null) {
            InterviewPersistence.candidateModel = CandidateMapper().map(candidate)
            view?.setLayoutCandidate()
        } else
            InterviewPersistence.candidateModel = null
    }

    override fun restartQuestionListOfInterview() {
        InterviewPersistence.quesitonList = arrayListOf()
    }

    override fun setTagList(tagList: List<Tag?>) {
        InterviewPersistence.tagsList = ArrayList(tagList)
        view?.populateTagList()
    }

    override fun goAddQuestionActivity() {
        val intent = Intent(context, SelectQuestionActivity::class.java)

        val interviewString = Gson().toJson(InterviewPersistence)
        intent.putExtra(INTERVIEW_MODEL, interviewString)

        view?.startSelectQuestionActivity(intent)
    }

    override fun castDateToString(dateSelected: Calendar) {
        InterviewPersistence.scheduleDate =
                "${dateSelected.get(Calendar.MONTH)}/${dateSelected.get(Calendar.DAY_OF_MONTH)}/${dateSelected.get(Calendar.YEAR)} ${dateSelected.get(Calendar.HOUR)}:${dateSelected.get((Calendar.MINUTE))}:00"
    }

    override fun sendRequest() {
        if (validateInterview()) {
            when (viewMode) {
                ViewMode.ADD -> addInterview()
                ViewMode.EDIT -> editInterview()
                ViewMode.DUPLICATE -> addInterview()
                ViewMode.VIEW -> {

                }
            }
        }
    }

    private fun addInterview() {
        val request = AddInterviewRequest(
                candidateId = InterviewPersistence.candidateModel!!.id.toString(),
                interviewersIds = InterviewPersistence.idInterviewers.map {
                    it.id
                },
                test = InterviewPersistence.quesitonList.map {
                    TestInterview(it.questionId, it.answer)
                },
                feedback = InterviewPersistence.feedback,
                tags = InterviewPersistence.tagsList.map {
                    it.toString()
                },
                scheduledDateTime = InterviewPersistence.scheduleDate,
                role = "ADMINISTRATOR")

        disposable.add(AddInterviewInteractor(App.ishApi)
                .invoke(request)
                .applySchedulers()
                .subscribeBy(
                        onSuccess = {
                            view?.showToast("Succes")
                            view?.finishActivity()
                        },
                        onError = {
                            view?.showToast(context.getString(R.string.errror_ocured))
                        }
                ))
    }

    private fun editInterview() {
        disposable.add(UpdateInterviewInteractor(App.ishApi)
                .invoke(EditInterviewRequest(
                        id = interview!!.id,
                        candidateId = InterviewPersistence.candidateModel!!.id,
                        interviewersIds = InterviewPersistence.idInterviewers.map { it.id },
                        test = InterviewPersistence.quesitonList.map {
                            TestModel(it.questionId, it.answer)
                        },
                        tags = InterviewPersistence.tagsList.map {
                            it.toString()
                        },
                        scheduledDateTime = InterviewPersistence.scheduleDate,
                        feedback = InterviewPersistence.feedback,
                        role = "ADMINISTRATOR"
                ))
                .applySchedulers()
                .subscribeBy(
                        onSuccess = {
                            if (it.return_code == 0)
                                view?.showToast(context.getString(R.string.interview_was_saved))
                            view?.finishActivity()
                        },
                        onError = {
                            view?.showToast(context.getString(R.string.errror_ocured))
                        }
                )
        )
    }


    private fun validateInterview(): Boolean {
        var validate = true
        val errorList: ArrayList<String> = arrayListOf()

        if (InterviewPersistence.candidateModel == null) {
            validate = false
            errorList.add(context.getString(R.string.interview_must_have_candidate))
        }

        if (InterviewPersistence.quesitonList.size < 2) {
            validate = false
            errorList.add(context.getString(R.string.interview_must_have_three_question))
        }

        if (InterviewPersistence.tagsList.isEmpty()) {
            validate = false
            errorList.add(context.getString(R.string.interview_must_to_hve_one_tag))
        }

        if (InterviewPersistence.scheduleDate.isBlank()) {
            validate = false
            errorList.add(context.getString(R.string.interview_must_to_have_date))
        }

        if (InterviewPersistence.idInterviewers.isEmpty()) {
            validate = false
            errorList.add(context.getString(R.string.interview_must_to_have_interviewers))
        }

        view?.showErrorDialog(errorList.toList())

        return validate
    }

    override fun showDialogProfile(isInterviewer: Boolean, peopleName: String, peopleEmail: String, tagList: List<Tag?>,
                                   peoplePhone: String?, peopleCv: String?, peopleTenicalLvl: String?, peopleNote: String?) {

        val builder = AlertDialog.Builder(context)
        if (isInterviewer) {
            builder.setTitle("Interviewer")
        } else
            builder.setTitle("Candidate")

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

        if (isInterviewer) {
            cvLayout.setInvisible()
            tehnicalLevelLayout.setInvisible()
            phoneLayout.setInvisible()
            noteLayout.setVisible()
            icon.setImageResource(R.drawable.ic_administrator)
        } else {
            cvLayout.setVisible()
            tehnicalLevelLayout.setVisible()
            phoneLayout.setVisible()
            noteLayout.setInvisible()
            icon.setImageResource(R.drawable.ic_candidate)
        }

        name.text = peopleName
        phone.text = peoplePhone
        email.text = peopleEmail
        tehnicalLevel.text = peopleTenicalLvl
        cv.text = peopleCv
        note.text = peopleNote

        val tehnologiesList = tagList
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

}