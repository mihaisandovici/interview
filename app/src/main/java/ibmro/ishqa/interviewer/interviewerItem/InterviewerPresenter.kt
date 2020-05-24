package ibmro.ishqa.interviewer.interviewerItem

import android.content.Context
import ibmro.common.extension.applySchedulers
import ibmro.datanetworksource.interactors.interviewer.AddInterviewerInteractor
import ibmro.datanetworksource.interactors.interviewer.UpdateInterviewerInteractor
import ibmro.datanetworksource.repositories.entities.databaseModels.requestModel.AddInterviewerRequest
import ibmro.datanetworksource.repositories.entities.databaseModels.requestModel.EditInterviewerRequest
import ibmro.ishqa.App
import ibmro.ishqa.R
import ibmro.ishqa.base.BasePresenterImpl
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy

class InterviewerPresenter(
        private var context: Context
) : BasePresenterImpl<InterviewerContract.View>(),
        InterviewerContract.Presenter {

    private var disposable = CompositeDisposable()


    override fun addInterviewer(body: AddInterviewerRequest) {
        disposable.add(AddInterviewerInteractor(App.ishApi)
                .invoke(body)
                .applySchedulers()
                .subscribeBy(
                        onSuccess = {
                            view?.showToast("Success")
                        },
                        onError = {
                            view?.showToast(context.getString(R.string.errror_ocured))
                        })
        )
    }

    override fun editInterviewer(body: EditInterviewerRequest) {
        disposable.add(UpdateInterviewerInteractor(App.ishApi)
                .invoke(body)
                .applySchedulers()
                .subscribeBy(
                        onSuccess = {
                            view?.showToast("Success")
                        },
                        onError = {
                            view?.showToast(context.getString(R.string.errror_ocured))
                        }
                ))
    }
}