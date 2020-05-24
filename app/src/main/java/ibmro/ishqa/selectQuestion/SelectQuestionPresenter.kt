package ibmro.ishqa.selectQuestion

import android.content.Context
import ibmro.common.extension.applySchedulers
import ibmro.datanetworksource.interactors.question.GetQuestionListInteractor
import ibmro.datanetworksource.interactors.question.GetQuestionsByIdInteractor
import ibmro.datanetworksource.repositories.entities.Tag
import ibmro.datanetworksource.repositories.entities.databaseModels.requestModel.GetQuestionsByTagRequest
import ibmro.datanetworksource.repositories.entities.databaseModels.responseModel.QuestionResponse
import ibmro.ishqa.App
import ibmro.ishqa.R
import ibmro.ishqa.base.BasePresenterImpl
import ibmro.ishqa.dashboard.sectionVM.getImage
import ibmro.ishqa.interview.interviewItem.QuestionModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy

class SelectQuestionPresenter(
        private var context: Context,
        private var tagList: List<Tag?>,
        private var returnedList: ArrayList<Int>
) : BasePresenterImpl<SelectQuestionContract.View>(),
        SelectQuestionContract.Presenter {

    var disposable = CompositeDisposable()

    var questionList = arrayListOf<QuestionResponse>()

//    var returnedList = arrayListOf<Int>()

    override fun attachView(view: SelectQuestionContract.View) {
        super.attachView(view)
        loadQuestions()
    }

    override fun loadQuestions() {
        val tagListString = tagList.map { it.toString() }
        view?.loading(true)

        disposable.add(GetQuestionListInteractor(App.ishApi)
                .invoke(GetQuestionsByTagRequest(tagListString, "INTERVIEWER", true))
                .applySchedulers()
                .subscribeBy(
                        onSuccess = {
                            questionList = ArrayList(it.questions)
                            view?.loading(false)
                            view?.populateQuestionList(questionList, tagList[0]!!.getImage(), returnedList.toList())
                        },
                        onError = {
                            view?.loading(false)
                        }
                )
        )
    }

    override fun addQuestionId(id: Int, isCheckd: Boolean) {
        when (isCheckd) {
            true -> {
                if (!returnedList.contains(id))
                    returnedList.add(id)
            }
            false -> {
                returnedList.remove(id)
            }
        }

    }

    override fun getQuestionSelected() {
        disposable.add(
                GetQuestionsByIdInteractor(App.ishApi)
                        .invoke(returnedList.toList())
                        .applySchedulers()
                        .subscribeBy(onSuccess = {
                            val questionModelList: List<QuestionModel> = it.map { questionResponse ->
                                QuestionModel(questionResponse.id, questionResponse.questionContent, 0)
                            }
                            view?.returnQuestionSelected(ReturnQuestionModel(questionModelList))
                        },
                                onError = {
                                    view?.showToast(context.getString(R.string.errror_ocured))
                                }

                        )
        )
    }

}

