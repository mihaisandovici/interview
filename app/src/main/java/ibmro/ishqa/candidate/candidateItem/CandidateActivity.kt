package ibmro.ishqa.candidate.candidateItem


import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View


import android.widget.Toast
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import ibmro.common.androidCommon.KEY_CANDIDATE_ID
import ibmro.common.androidCommon.KEY_CANDIDATE_VIEW

import ibmro.common.extension.clickOn
import ibmro.common.extension.onChange
import ibmro.common.extension.stringToEditable
import ibmro.datanetworksource.services.mappers.TagListMapper
import ibmro.ishqa.R
import ibmro.ishqa.base.BaseActivity
import ibmro.ishqa.interview.interviewsList.tagsRecycler.TagAdapterFlexBox
import ibmro.ishqa.profile.*

import ibmro.ishqa.selectTags.TagActivity
import kotlinx.android.synthetic.main.activity_new_candidate.*


class CandidateActivity : BaseActivity<CandidateContract.View,
        CandidateContract.Presenter>(),
        CandidateContract.View  {


    override lateinit var presenter: CandidateContract.Presenter
    private var candidateId=0
    private var editable=false
    private var setLayoutOnce = false

    override fun onCreate(savedInstanceState: Bundle?) {
        candidateId=intent.getIntExtra(KEY_CANDIDATE_ID,-1)
        editable=intent.getBooleanExtra(KEY_CANDIDATE_VIEW,false)
        presenter=CandidatePresenter(candidateId, editable, this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_candidate)

    }

    override fun onStart() {
        super.onStart()
        presenter.setLayoutMode(setLayoutOnce)
        setLayoutOnce = true
        doneBtn.clickOn{
            presenter.save()
        }

    }

    override fun setCandidate(firstName : String?, lastName : String?, phoneNr : String?,
                              email : String?, technicalLevel : String?, prefTechnologies : List<String>,
                              tags : List<String>, cv : String?) {

        CandidatePersistent.firstName= firstName
        CandidatePersistent.lastName=lastName
        CandidatePersistent.phoneNr=phoneNr
        CandidatePersistent.email= email
        CandidatePersistent.technicalLevel=  technicalLevel
        CandidatePersistent.prefTechnologies= prefTechnologies
        CandidatePersistent.tags=tags
        CandidatePersistent.candidateCV=cv
    }

    override fun replaceCandidateContent(){
        if(!editable) {

            firstNameText.text=stringToEditable(CandidatePersistent.firstName+" "+ CandidatePersistent.lastName)
            //lastNameText.text = stringToEditable(CandidatePersistent.lastName)
            phoneNrText.text = stringToEditable(CandidatePersistent.phoneNr!!)
            emailText.text = stringToEditable(CandidatePersistent.email!!)
            technicalLevelText.text = stringToEditable(CandidatePersistent.technicalLevel!!)
            setUpCV()
        }
        else {
            if (CandidatePersistent.firstName != null)
                firstNameEditText.text = stringToEditable(CandidatePersistent.firstName!!)
            else firstNameEditText.text = stringToEditable("")
            if (CandidatePersistent.lastName != null)
                lastName.text = stringToEditable(CandidatePersistent.lastName!!)
            else  lastName.text = stringToEditable("")
            if (CandidatePersistent.phoneNr != null)
                phoneNr.text = stringToEditable(CandidatePersistent.phoneNr!!)
            else  phoneNr.text = stringToEditable("")
            if(CandidatePersistent.email!=null)
             email.text = stringToEditable(CandidatePersistent.email!!)
            else email.text= stringToEditable("")
            if(CandidatePersistent.technicalLevel!=null)
                technicalLevel.text = stringToEditable(CandidatePersistent.technicalLevel!!)
            else technicalLevel.text = stringToEditable("")
            if (CandidatePersistent.candidateCV != null)
                cv.text = stringToEditable(CandidatePersistent.candidateCV!!)
            else cv.text = stringToEditable("")

        }

    }

    private fun setUpCV(){
        if(CandidatePersistent.candidateCV!="" && CandidatePersistent.candidateCV!=null){
            cvText.visibility=View.VISIBLE
            cvText.clickOn {
                /*
                val browserIntent =Intent(Intent.ACTION_VIEW, Uri.parse(CandidatePersistent.candidateCV))
                startActivity(browserIntent)*/
                val webViewIntent = Intent(this,WebViewActivity::class.java)
                        .putExtra("CV",CandidatePersistent.candidateCV)
                startActivity(webViewIntent)

            }
        }
        else cvText.visibility=View.GONE
    }

    override fun setTags(editable: Boolean) {

        val flexboxLayoutManager = FlexboxLayoutManager(this).apply {
                flexWrap = FlexWrap.WRAP
                flexDirection = FlexDirection.ROW
                alignItems = AlignItems.STRETCH
            }
        if(!editable) {
            candidate_tag_recycler_view.visibility = View.VISIBLE
            candidate_tag_recycler_view.apply {
                layoutManager = flexboxLayoutManager
                //adapter = TagAdapterFlexbox(ArrayList(tagList), editable)
                adapter = TagAdapterFlexBox(TagListMapper().map(CandidatePersistent.tags))
            }
        }
        else {
            candidate_tag_recycler_view_editable.visibility=View.VISIBLE
            candidate_tag_recycler_view_editable.apply {
                layoutManager = flexboxLayoutManager
                // adapter = TagAdapterFlexbox(ArrayList(tagList), editable)
                adapter = TagAdapterFlexBox(TagListMapper().map(CandidatePersistent.tags))
            }
        }

    }

    override fun setLayoutMode(stringResource: Int) {
        setTitle(stringResource)
        if(!editable ){
            editableLayout.visibility=View.GONE
            uneditableLayout.visibility=View.VISIBLE
            val fullName="${CandidatePersistent.firstName}  ${CandidatePersistent.lastName}"
            firstNameText.text= fullName
            //lastNameText.text=CandidatePersistent.lastName
            phoneNrText.text=CandidatePersistent.phoneNr
            emailText.text=CandidatePersistent.email
            technicalLevelText.text=CandidatePersistent.technicalLevel
            setUpCV()
        }
        else {
             if(candidateId!=-1) {
                 editableLayout.visibility=View.VISIBLE
                 uneditableLayout.visibility=View.GONE
                 newCandidateTextView.text = getString(R.string.candidate)
             }
            firstNameEditText.onChange { CandidatePersistent.firstName = it }
            lastName.onChange { CandidatePersistent.lastName = it }
            phoneNr.onChange { CandidatePersistent.phoneNr = it }
            email.onChange { CandidatePersistent.email = it }
            technicalLevel.onChange { CandidatePersistent.technicalLevel = it }
            cv.onChange { CandidatePersistent.candidateCV = it }
            add_tag_for_candidate_btn.clickOn {
                startTagActivity(false)
            }
            addPrefTech.clickOn{
                startTagActivity(true)
            }

        }
        setPrefTechnologiesLayout()
        setTags(editable)

    }

    private fun setFlexBowLayoutManager() : FlexboxLayoutManager{
        val flexboxLayoutManager = FlexboxLayoutManager(this).apply {
            flexWrap = FlexWrap.WRAP
            flexDirection = FlexDirection.ROW
            alignItems = AlignItems.STRETCH
        }
        return flexboxLayoutManager
    }

    override fun setPrefTechnologiesLayout() {

        val flexboxLayoutManager=setFlexBowLayoutManager()
        if(!editable) {
            prefTechnologiesText.text=getString(R.string.prefered_technologies)
            candidate_prefTech_recycler_view.visibility = View.VISIBLE
            candidate_prefTech_recycler_view.apply {
                layoutManager = flexboxLayoutManager
                adapter = TagAdapterFlexbox(ArrayList(CandidatePersistent.prefTechnologies), editable)
                //adapter = TagAdapterFlexBox(TagListMapper().map(CandidatePersistent.prefTechnologies))
            }
        }
        else{
            candidate_prefTech_recycler_view_editable.visibility = View.VISIBLE
            candidate_prefTech_recycler_view_editable.apply {
                layoutManager = flexboxLayoutManager
                adapter = TagAdapterFlexbox(ArrayList(CandidatePersistent.prefTechnologies), editable)
                //adapter = TagAdapterFlexBox(TagListMapper().map(CandidatePersistent.prefTechnologies))
            }
        }

    }

    override fun showToast(text: String) {
        Toast.makeText(this,text,Toast.LENGTH_LONG).show()
    }

    private fun startTagActivity(showTechnologies : Boolean) {
        val tagListString : ArrayList<String>
        val intent : Intent
        if(showTechnologies){
            tagListString = ArrayList(CandidatePersistent.prefTechnologies)
            intent = Intent(this, TagActivity::class.java)
            intent.putExtra(TECHNOLOGIES,true)
        }
        else{
            tagListString=ArrayList(CandidatePersistent.tags)
            intent = Intent(this, TagActivity::class.java)
        }
        val bundle = Bundle()
        bundle.putStringArrayList(PROFILE_TAGS, tagListString)
        intent.putExtras(bundle)

        startActivityForResult(intent, RESULT_LOAD_TAGS)

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RESULT_LOAD_TAGS) {
            if (resultCode == Activity.RESULT_OK) {
                val bundle = data?.extras
                val tags = bundle?.getStringArrayList(PROFILE_TAG_RESULT)
                val selectTechnology=data?.getBooleanExtra(TECHNOLOGIES,false)
                if(selectTechnology!!) {
                    if (tags != null)
                        presenter.setPrefTech(tags)

                    else presenter.setPrefTech(arrayListOf())

                }
                else {
                    if (tags != null)
                        presenter.setTags(tags)
                    else presenter.setTags(arrayListOf())
                }
            }
        }
    }

    override fun back() {
        this.onBackPressed()
    }

    override fun showErrorDialog(errorList: List<String>) {
        var errorString = ""
        if(errorList.isEmpty())
            finish()
        else {
            errorList.map {
                errorString = "$errorString \n $it "
            }

            val builder = AlertDialog.Builder(this)
                    .setMessage(errorString)
                    .setTitle(getString(R.string.candidate_is_not_complete))
                    .setIcon(R.drawable.ic_alert)
                    .setPositiveButton(android.R.string.ok) { dialog, _ ->
                        dialog.dismiss()
                    }
            builder.show()
        }
    }
}
