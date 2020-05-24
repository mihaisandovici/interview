package ibmro.ishqa.question.questionItem

import android.content.Context
import ibmro.common.extension.applySchedulers
import ibmro.datanetworksource.interactors.question.AddQuestionInteractor
import ibmro.datanetworksource.interactors.question.DeleteQuestionInteractor
import ibmro.datanetworksource.interactors.question.GetQuestionByIdInteractor
import ibmro.datanetworksource.interactors.question.UpdateQuestionInteractor
import ibmro.datanetworksource.repositories.entities.Tag
import ibmro.datanetworksource.repositories.entities.databaseModels.requestModel.DeleteRequest
import ibmro.datanetworksource.repositories.entities.databaseModels.requestModel.UpdateQuestionRequest
import ibmro.ishqa.App
import ibmro.ishqa.R
import ibmro.ishqa.base.BasePresenterImpl
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy

class QuestionPresenter(private var questionId: Int,
                        private var editMode: Boolean,
                        private var context: Context
) : BasePresenterImpl<QuestionContract.View>(),
        QuestionContract.Presenter {

    private val disposable: CompositeDisposable = CompositeDisposable()

    override fun save() {
        var errorMsg: Throwable

        if (validateQuestion().isEmpty()) {

            if (questionId == -1) {
                disposable.add(AddQuestionInteractor(App.ishApi)
                        .invoke(QuestionRequestMapper().map(QuestionPersistent))
                        .applySchedulers()
                        .subscribeBy(
                                onError = {
                                    view?.showToast(context.getString(R.string.errror_ocured))
                                    errorMsg = it
                                },
                                onSuccess = {
                                    if (it.return_code == 40) {
                                        view?.showToast(context.getString(R.string.internal_server_error))
                                    } else {
                                        view?.showToast(context.getString(R.string.question_has_been_added))
                                        view?.back()
                                    }
                                }
                        ))
            } else {
                val acceptList: ArrayList<String> = arrayListOf()
                val suggestedList: ArrayList<String> = arrayListOf()
                QuestionPersistent.answerList.map {
                    if (it.correct) {
                        acceptList.add(it.answerContent)
                    } else
                        suggestedList.add(it.answerContent)
                }

                disposable.add(UpdateQuestionInteractor(App.ishApi)
                        .invoke(UpdateQuestionRequest(
                                id = questionId.toString(),
                                tags = QuestionPersistent.tagList.map {
                                    it.toString()
                                },
                                questionContent = QuestionPersistent.questionContent,
                                acceptedAnswer = acceptList,
                                suggestedAnswer = suggestedList,
                                createdBy = "1",
                                modifiedBy = "1",
                                contentFile = "",
                                role = "ADMINISTRATOR"
                        )).applySchedulers()
                        .subscribeBy(
                                onSuccess = {
                                    view?.showToast(context.getString(R.string.question_has_been_changed))
                                },
                                onError = {
                                    view?.showToast(context.getString(R.string.errror_ocured))
                                }
                        ))
            }
        } else view?.showErrorDialog(validateQuestion())

    }

    override fun setLayoutMode(setLayoutOnce: Boolean) {
        if (questionId == -1) {
            view?.setLayoutMode(R.string.add_question, true)
            loadAddQuestion(setLayoutOnce)
        } else if (editMode) {
            view?.setLayoutMode(R.string.edit_question, true)
            loadQuestion(setLayoutOnce)
        } else {
            view?.setLayoutMode(R.string.view_question, false)
            loadQuestion(setLayoutOnce)

        }
    }

    override fun attachView(view: QuestionContract.View) {
        super.attachView(view)
    }

    private fun loadQuestion(setLayoutOnce: Boolean) {
        if (!setLayoutOnce) {
            disposable.add(GetQuestionByIdInteractor(App.ishApi).invoke(questionId)
                .applySchedulers()
                .subscribeBy(
                        onSuccess = { questionResponse ->
                            val tagList = questionResponse.tags.map { tagString ->
                                Tag.getItemByString(tagString)
                            }
                            val questionContent = questionResponse.questionContent

                            val answerList = ArrayList(questionResponse.acceptedAnswer.map {
                                AnswerModel(it, true)
                            })
                            questionResponse.suggestedAnswer.map {
                                answerList.add(AnswerModel(it, false))
                            }

                            view?.setQuestion(questionContent, ArrayList(tagList), answerList)
                            view?.replaceQuestionContent(editMode)
                            view?.setTagsLayout()
                        },
                        onError = {

                        }
                )
        )
        }

        view?.setTagsLayout()
    }

    private fun loadAddQuestion(setLayoutOnce: Boolean) {
        if (!setLayoutOnce)
            view?.setQuestion("", arrayListOf(), arrayListOf(AnswerModel("", false)))

        view?.replaceQuestionContent(true)
        view?.setTagsLayout()
    }

    fun validateQuestion(): List<String> {
        var validate = true
        val errorList: ArrayList<String> = arrayListOf()

        if (QuestionPersistent.questionContent.length < 3) {
            validate = false
            errorList.add(context.getString(R.string.validate_question_content))
        }

        if (QuestionPersistent.tagList.isEmpty()) {
            validate = false
            errorList.add(context.getString(R.string.validate_tag_list))
        }

        if (QuestionPersistent.answerList.isEmpty()) {
            validate = false
            errorList.add(context.getString(R.string.validate_have_answer))
        } else {
            var allAnswerHaveConent = true
            var oneAnswerTrue = false
            QuestionPersistent.answerList.map {
                if (it.answerContent.isBlank()) {
                    allAnswerHaveConent = false
                }
                if (it.correct) {
                    oneAnswerTrue = true
                }
            }

            if (!allAnswerHaveConent) {
                validate = false
                errorList.add(context.getString(R.string.all_answer_contain_content))
            }

            if (!oneAnswerTrue) {
                validate = false
                errorList.add(context.getString(R.string.validate_correct_answer))
            }


        }
        return errorList.toList()
    }

    override fun deleteQuestion() {
        var errorMsg: Throwable
        disposable.add(DeleteQuestionInteractor(App.ishApi)
                .invoke(DeleteRequest(questionId.toString(), "ADMINISTRATOR"))
                .applySchedulers()
                .subscribeBy(
                        onError = {
                            errorMsg = it
                            view?.showToast(context.getString(R.string.errror_ocured))
                        },
                        onSuccess = {
                            view?.showToast(context.getString(R.string.question_has_been_deleted))
                            view?.back()
                        }
                ))
    }

    override fun setTags(tagList: ArrayList<String>?) {
        if (tagList != null) {
            QuestionPersistent.tagList = tagList.map { tagString ->
                Tag.getItemByString(tagString)
            }
        }
        view?.setTagsLayout()
    }


}