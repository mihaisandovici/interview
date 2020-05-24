package ibmro.ishqa.interview.interviewsList

import android.content.Context
import ibmro.common.extension.applySchedulers
import ibmro.datanetworksource.interactors.candidate.GetCandidateByIdInteractor
import ibmro.datanetworksource.interactors.interview.DeleteInterviewInteractor
import ibmro.datanetworksource.interactors.interview.GetInterviewsListInteractor
import ibmro.datanetworksource.repositories.entities.databaseModels.requestModel.GetInterviewsRequest
import ibmro.datanetworksource.repositories.entities.viewModels.InterviewViewModel
import ibmro.datanetworksource.services.mappers.CandidateMapper
import ibmro.datanetworksource.services.mappers.InterviewMapper
import ibmro.ishqa.App
import ibmro.ishqa.R
import ibmro.ishqa.base.BasePresenterImpl
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import java.util.*

class InterviewsListPresenter constructor(
        private var context: Context

) : BasePresenterImpl<InterviewsListContract.View>(),
        InterviewsListContract.Presenter {

    private val disposable: CompositeDisposable = CompositeDisposable()
    var errorMsg: Throwable = Throwable()

    override fun attachView(view: InterviewsListContract.View) {
        super.attachView(view)
        view.setTitle()
        loadInterviewsList()
    }

    override fun loadInterviewsList() {
        view?.loading(true)
        disposable.add(GetInterviewsListInteractor(App.ishApi)
                .invoke(GetInterviewsRequest(null, null, null, "INTERVIEWER"))
                .applySchedulers()
                .subscribeBy(
                        onSuccess = { interviewResponse ->
                            val interviewList: ArrayList<InterviewViewModel> = arrayListOf()
                            interviewResponse.interviews.map { interview ->
                                disposable.add(GetCandidateByIdInteractor(App.ishApi).invoke(
                                        interview.candidateId, "ADMINISTRATOR"
                                ).applySchedulers()
                                        .subscribeBy(
                                                onSuccess = { candidateResponse ->
                                                    val interviewModel = InterviewMapper().map(interview)
                                                    interviewModel.candidate = CandidateMapper().map(candidateResponse.candidates)
                                                    interviewList.add(interviewModel)

                                                    view?.replaceListContent(interviewList)
                                                    view?.loading(false)
                                                },
                                                onError = {
                                                    errorMsg = it
                                                }
                                        ))
                            }

                        },
                        onError = {
                            errorMsg = it
                        }
                ))
    }

    override fun deleteInterview(interviewId: Int) {
        disposable.add(DeleteInterviewInteractor(App.ishApi)
                .invoke(interviewId, "ADMINISTRATOR")
                .applySchedulers()
                .subscribeBy(
                        onSuccess = {
                            view?.showToast("Interviul a fost sters cu succes")
                        },
                        onError = {
                            view?.showToast(context.getString(R.string.errror_ocured))
                        }
                ))
    }


}