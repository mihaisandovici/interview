package ibmro.ishqa.question.questionList

import android.content.Context
import ibmro.common.extension.applySchedulers
import ibmro.datanetworksource.interactors.question.DeleteQuestionInteractor
import ibmro.datanetworksource.interactors.question.GetQuestionListInteractor
import ibmro.datanetworksource.interactors.sections.GetSectionByTagInteractor
import ibmro.datanetworksource.repositories.entities.databaseModels.requestModel.DeleteRequest
import ibmro.datanetworksource.repositories.entities.databaseModels.requestModel.GetQuestionsByTagRequest
import ibmro.ishqa.App
import ibmro.ishqa.R
import ibmro.ishqa.base.BasePresenterImpl
import ibmro.ishqa.dashboard.sectionVM.SectionMapper
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy

class QuestionListPresenter(
        private var context: Context,
        private var mainTagName: String,
        private var itemTagName: String
) : BasePresenterImpl<QuestionListContract.View>(), QuestionListContract.Presenter {

    private val disposable: CompositeDisposable

    init {
        disposable = CompositeDisposable()
    }

    override fun loadSectionItem() {
        disposable.add(GetSectionByTagInteractor()
                .invoke(mainTagName)
                .applySchedulers()
                .subscribe { section ->
                    val sectionItem = SectionMapper(context).map(section).listSectionItem.first {
                        it.itemTag.toString() == itemTagName
                    }

                    view?.setTitle(sectionItem)
                })
    }

    override fun attachView(view: QuestionListContract.View) {
        super.attachView(view)
        loadSectionItem()
        loadQuestionList()
    }

    override fun loadQuestionList() {
        var errorMsg: Throwable

        view?.progressbarVisibility(true)

        disposable.add(GetQuestionListInteractor(App.ishApi)
                .invoke(GetQuestionsByTagRequest(listOf(itemTagName, mainTagName), "INTERVIEWER", false))
                .applySchedulers()

                .subscribeBy(onError = {
                    errorMsg = it
                    view?.errorMessageVisibility(true)
                    view?.progressbarVisibility(false)
                },
                        onSuccess = {
                            view?.progressbarVisibility(false)
                            view?.errorMessageVisibility(false)
                            view?.replaceListContent(it.questions)
                            view?.setQuestionCount(it.questions.size)
                        }
                )
        )
    }

    override fun deleteQuestion(idQuestion: Int) {
        var errorMsg: Throwable

        view?.progressbarVisibility(true)

        disposable.add(DeleteQuestionInteractor(App.ishApi)
                .invoke(DeleteRequest(idQuestion.toString(), "ADMINISTRATOR"))
                .applySchedulers()
                .subscribeBy(
                        onError = {
                            errorMsg = it
                            view?.showToast(context.getString(R.string.errror_ocured))
                            view?.progressbarVisibility(false)

                        },
                        onSuccess = {
                            view?.showToast(context.getString(R.string.question_has_been_deleted))
                            view?.progressbarVisibility(false)
                            loadQuestionList()
                        }
                ))
    }



}