package ibmro.ishqa.selectTags

import ibmro.datanetworksource.repositories.entities.TagItem
import ibmro.ishqa.base.BasePresenter
import ibmro.ishqa.base.BaseView

class TagContract {


    interface View : BaseView{
        fun replaceAdapterContent(tagList: ArrayList<TagItem>)

        fun showList(tagList: ArrayList<String>)
    }

    interface Presenter : BasePresenter<View> {

        fun setTagItemList()

        fun check(tagName: String, check: Boolean)

        fun getTagList()

    }
}
