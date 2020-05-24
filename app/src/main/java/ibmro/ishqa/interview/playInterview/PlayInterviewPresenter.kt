package ibmro.ishqa.interview.playInterview

import android.content.Context
import com.google.gson.Gson
import ibmro.common.extension.applySchedulers
import ibmro.datanetworksource.interactors.interview.UpdateInterviewInteractor
import ibmro.datanetworksource.interactors.question.GetQuestionByIdInteractor
import ibmro.datanetworksource.repositories.entities.databaseModels.requestModel.EditInterviewRequest
import ibmro.datanetworksource.repositories.entities.databaseModels.requestModel.TestModel
import ibmro.datanetworksource.repositories.entities.viewModels.InterviewViewModel
import ibmro.ishqa.App
import ibmro.ishqa.R
import ibmro.ishqa.base.BasePresenterImpl
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy

class PlayInterviewPresenter(
        private var context: Context,
        interviewString: String
) : BasePresenterImpl<PlayInterviewContract.View>(),
        PlayInterviewContract.Presenter {

    private var disposable = CompositeDisposable()

    private var questionIterator = 0
    var interview = Gson().fromJson(interviewString, InterviewViewModel::class.java)

    override fun attachView(view: PlayInterviewContract.View) {
        super.attachView(view)
        getQuestion()
    }

    override fun getQuestion() {
        if (questionIterator < interview.test.size) {
            view?.loading(true)
            disposable.add(GetQuestionByIdInteractor(App.ishApi)
                    .invoke(interview.test[questionIterator].question)
                    .applySchedulers()
                    .subscribeBy(
                            onSuccess = {
                                view?.setQuestion(it)
                                view?.loading(false)
                            },
                            onError = {
                                view?.showToast(context.getString(R.string.errror_ocured))
                                view?.loading(false)
                            })
            )

        } else {
            view?.changeLayout()
        }
    }

    override fun setAnswer(result: Int) {
        interview.test[questionIterator].questionResult = result
        questionIterator++
    }

    override fun done(feedback: String) {
        interview.feedback = feedback
        disposable.add(UpdateInterviewInteractor(App.ishApi)
                .invoke(EditInterviewRequest(
                        id = interview.id,
                        candidateId = interview.candidate!!.id,
                        interviewersIds = interview.interviewersId,
                        test = interview.test.map {
                            TestModel(it.question, it.questionResult)
                        },
                        tags = interview.tags.map {
                            it.toString()
                        },
                        scheduledDateTime = interview.date,
                        feedback = interview.feedback,
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

}