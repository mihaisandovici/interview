package ibmro.ishqa.selectTags

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import ibmro.common.extension.clickOn
import ibmro.datanetworksource.repositories.entities.TagItem
import ibmro.ishqa.R
import ibmro.ishqa.base.BaseActivity
import ibmro.ishqa.profile.PROFILE_TAGS
import ibmro.ishqa.profile.PROFILE_TAG_RESULT
import ibmro.ishqa.profile.TECHNOLOGIES
import kotlinx.android.synthetic.main.activity_tag.*


class TagActivity : BaseActivity<TagContract.View, TagContract.Presenter>(), TagContract.View {

    lateinit override var presenter: TagContract.Presenter
    private var selectTechnologies : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
         selectTechnologies=intent.getBooleanExtra(TECHNOLOGIES,false)
        val selectedTags=intent.extras!!.getStringArrayList(PROFILE_TAGS) as ArrayList<String>?
        presenter = TagPresenter(selectedTags, selectTechnologies)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tag)

        select_tags.clickOn {
            presenter.getTagList()
        }
    }

    override fun replaceAdapterContent(tagList: ArrayList<TagItem>) {
        tag_recycler_view.layoutManager = LinearLayoutManager(this)
        tag_recycler_view.adapter = TagsAdapter(tagList, object : CheckContract {
            override fun check(tagName: String, check: Boolean) {
                presenter.check(tagName, check)
            }

        })
        tag_recycler_view.setHasFixedSize(true)
    }

    override fun showList(tagList: ArrayList<String>) {

        val returnIntent = Intent()
          val bundle =  Bundle()
          bundle.putStringArrayList(PROFILE_TAG_RESULT, tagList)
          returnIntent.putExtras(bundle)
          returnIntent.putExtra(TECHNOLOGIES,selectTechnologies)
          setResult(Activity.RESULT_OK, returnIntent)
          finish()

    }


//    private fun addAdapter() {
//        val viewManager = LinearLayoutManager(this)
//        viewAdapter=TagsAdapter(presenter.getTagItems())
//
//        tag_recycler_view.apply{
//            layoutManager = viewManager
//            adapter = viewAdapter
//        }
//    }
//
//    private fun initializeTags() {
//        val bundle = intent!!.extras
//        presenter.setSelectedTags(bundle)
//
//    }
//
//    override fun onClick(v: View?) {
//        if(v!!.id==R.id.select_tags)
//        {
//            val listTagItem=(viewAdapter as TagsAdapter).getSelectedTags()
//            presenter.setProfileTags(listTagItem)
//            val returnIntent = Intent()
//            val bundle =  Bundle()
//            bundle.putSerializable(PROFILE_TAG_RESULT, presenter.getProfileTags())
//            returnIntent.putExtras(bundle)
//            setResult(Activity.RESULT_OK, returnIntent)
//            finish()
//        }
//
//    }


}
