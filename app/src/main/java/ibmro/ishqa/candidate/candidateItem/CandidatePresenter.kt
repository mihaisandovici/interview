package ibmro.ishqa.candidate.candidateItem

import android.content.Context
import ibmro.common.extension.applySchedulers
import ibmro.datanetworksource.interactors.candidate.AddCandidateInteractor
import ibmro.datanetworksource.interactors.candidate.GetCandidateByIdInteractor
import ibmro.datanetworksource.interactors.candidate.UpdateCandidateInteractor
import ibmro.datanetworksource.repositories.entities.databaseModels.requestModel.UpdateCandidateRequest
import ibmro.ishqa.App
import ibmro.ishqa.R
import ibmro.ishqa.base.BasePresenterImpl
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy


class CandidatePresenter(private val candidateId : Int,private val editable : Boolean, private val context : Context) : BasePresenterImpl<CandidateContract.View>(), CandidateContract.Presenter {


    private val disposable: CompositeDisposable

    init {
        disposable = CompositeDisposable()
    }

    override fun setLayoutMode(setLayoutOnce: Boolean) {
        if (candidateId == -1) {
            view?.setLayoutMode(R.string.add_candidate)//, true)
            loadAddCandidate(setLayoutOnce)
        }
        else
            if(editable) {
                view?.setLayoutMode(R.string.update_candidate)
                loadCandidateById(setLayoutOnce)
            }
        else {
                view?.setLayoutMode(R.string.view_candidate)
                loadCandidateById(setLayoutOnce)
            }
    }

    private fun loadAddCandidate(setLayoutOnce : Boolean){//(layoutOnce: Boolean) {
        if (!setLayoutOnce)
        view?.setCandidate("", "", "",
                    "", "", arrayListOf(), arrayListOf(), "")
        view?.replaceCandidateContent()//(true)
        //view?.setTagsLayout()
        view?.setTags(editable)
        view?.setPrefTechnologiesLayout()
    }

    override fun save() {
        var errorMsg: Throwable

        if (validateCandidate().isEmpty()) {

            if (candidateId == -1) {
                disposable.add(AddCandidateInteractor(App.ishApi)
                        .invoke(CandidateRequestMapper().map(CandidatePersistent))
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
                                        view?.showToast(context.getString(R.string.candidate_has_been_added))
                                        view?.back()
                                    }
                                }
                        ))
            }

            else {

                disposable.add(UpdateCandidateInteractor(App.ishApi)
                        .invoke(UpdateCandidateRequest(
                                id=candidateId.toString(),
                                firstName = CandidatePersistent.firstName,
                                lastName = CandidatePersistent.lastName,
                                phoneNr = CandidatePersistent.phoneNr,
                                email = CandidatePersistent.email,
                                technicalLevel =  CandidatePersistent.technicalLevel,
                                prefTechnologies = CandidatePersistent.prefTechnologies,
                                tags = CandidatePersistent.tags,
                                candidateCV = CandidatePersistent.candidateCV
                        ))

                        .applySchedulers()
                        .subscribeBy(
                                onSuccess = { view?.showToast(context.getString(R.string.candidate_has_been_changed))
                                },
                                onError = {
                                    view?.showToast(context.getString(R.string.errror_ocured))
                                }
                        )

                )

            }

        }
             view?.showErrorDialog(validateCandidate())


    }

    private fun validateCandidate() :  List<String> {

        val errorList: ArrayList<String> = arrayListOf()


        if(!checkLength(CandidatePersistent.firstName))
            errorList.add(context.getString(R.string.validate_candidate_firstname))

        if(!checkLength(CandidatePersistent.lastName))
            errorList.add(context.getString(R.string.validate_candidate_lastname))

        if(!checkLength(CandidatePersistent.email))
            errorList.add(context.getString(R.string.validate_candidate_email))

        if(!checkLength(CandidatePersistent.phoneNr))
            errorList.add(context.getString(R.string.validate_candidate_phoneNr))

        if( !checkLength(CandidatePersistent.technicalLevel))
            errorList.add(context.getString(R.string.validate_candidate_technicalLvl))

        if(CandidatePersistent.prefTechnologies.isEmpty())
            errorList.add(context.getString(R.string.validate_preferred_technologies))

        if (CandidatePersistent.tags.isEmpty())
            errorList.add(context.getString(R.string.validate_tag_list_for_candidate))


        return errorList
        }


    private fun checkLength(input : String?) : Boolean {
        if (input == null || input.length < 3)
                return false
        return true
    }

    override fun loadCandidateById(setLayoutOnce: Boolean) {
        if(!setLayoutOnce) {
            disposable.add(GetCandidateByIdInteractor(App.ishApi)
                    .invoke(candidateId, "ADMINISTRATOR")
                    .applySchedulers()
                    .subscribeBy(
                            onSuccess = { candidateResponse ->
                                /* val tagList = candidateResponse.tags.map { tagString ->
                                          Tag.getItemByString(tagString)}*/
                                val phoneNum=candidateResponse.candidates.phoneNr
                                val phoneNrString="0$phoneNum"
                                view?.setCandidate(candidateResponse.candidates.firstName,candidateResponse.candidates.lastName,phoneNrString,
                                        candidateResponse.candidates.email, candidateResponse.candidates.technicalLevel,
                                        candidateResponse.candidates.prefTechnologies,  candidateResponse.candidates.tags,  candidateResponse.candidates.candidateCV)

                                view?.replaceCandidateContent()
                                view?.setTags(editable)
                                view?.setPrefTechnologiesLayout()

                            },
                            onError = {

                            }
                    ))
        }
    }


    override fun setTags(tags : ArrayList<String>) {

        CandidatePersistent.tags=tags
        view?.setTags(editable)
    }

    override fun setPrefTech(tags: ArrayList<String>) {
        CandidatePersistent.prefTechnologies=tags
        view?.setPrefTechnologiesLayout()
    }

    override fun deleteCandidate() {

    }

}