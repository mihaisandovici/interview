package ibmro.ishqa.candidate.candidateList

import android.content.Context
import ibmro.common.extension.applySchedulers
import ibmro.datanetworksource.interactors.candidate.DeleteCandidateInteractor
import ibmro.datanetworksource.interactors.candidate.GetCandidateListInteractor
import ibmro.datanetworksource.repositories.entities.databaseModels.requestModel.DeleteCandidateRequest
import ibmro.datanetworksource.repositories.entities.databaseModels.requestModel.GetCandidatesRequest
import ibmro.ishqa.App
import ibmro.ishqa.R
import ibmro.ishqa.base.BasePresenterImpl
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy

class CandidateListPresenter(private var context: Context,private var checkable : Boolean)
    : BasePresenterImpl<CandidateListContract.View>(), CandidateListContract.Presenter {

    private val disposable: CompositeDisposable

    init {
        disposable = CompositeDisposable()
    }

    override fun attachView(view: CandidateListContract.View) {
        super.attachView(view)
        view.setTitle()
        loadCandidateList()
    }

    override fun loadCandidateList() {

        var errorMsg: Throwable

        view?.progressbarVisibility(true)

        disposable.add(GetCandidateListInteractor(App.ishApi)
                .invoke(GetCandidatesRequest("ADMINISTRATOR"))
                .applySchedulers()

                .subscribeBy(onError = {
                    errorMsg = it
                    view?.errorMessageVisibility(true)
                    view?.progressbarVisibility(false)
                },
                        onSuccess = {
                            view?.progressbarVisibility(false)
                            view?.errorMessageVisibility(false)
                            view?.replaceCandidateList(it.candidates,checkable)
                        }
                )
        )
    }
    override fun deleteCandidate(candidateId : Int) {
        var errorMsg : Throwable
        disposable.add(DeleteCandidateInteractor(App.ishApi)
                .invoke(DeleteCandidateRequest(candidateId.toString()))
                .applySchedulers()
                .subscribeBy(
                        onError = {
                            errorMsg = it
                            view?.showToast(context.getString(R.string.errror_ocured))
                            view?.progressbarVisibility(false)

                        },
                        onSuccess = {
                            view?.showToast(context.getString(R.string.candidate_has_been_deleted))
                            view?.progressbarVisibility(false)
                            loadCandidateList()
                        }
                )

        )

    }

}