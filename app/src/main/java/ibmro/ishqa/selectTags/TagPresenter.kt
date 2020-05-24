package ibmro.ishqa.selectTags

import ibmro.datanetworksource.repositories.SectionRepo
import ibmro.datanetworksource.repositories.entities.Tag
import ibmro.datanetworksource.repositories.entities.TagItem
import ibmro.ishqa.base.BasePresenterImpl


class TagPresenter(
        private var tagList: ArrayList<String>?,
        private var selectTechnologies : Boolean
) : BasePresenterImpl<TagContract.View>(),
        TagContract.Presenter {

    private var tagItemList = arrayListOf<TagItem>()

    override fun setTagItemList() {
        val tags : Array<Tag>
        if(selectTechnologies){
           // MainSections.
           // tags=SectionRepo.getMainSectionByTag("Topics")
            tags=arrayOf(Tag.Java,
                    Tag.Android,
                    Tag.Kotlin,
                    Tag.IOS,
                    Tag.Swift,
                    Tag.JavaScript,
                    Tag.SQL,
                    Tag.DataStructure,
                    Tag.DesignPatterns,
                    Tag.Algorithms,
                    Tag.GIT)
        }
        else tags=Tag.values()

        if (tags.isNotEmpty())
            tags.sortWith(Comparator { tag1, tag2 -> tag1.toString().compareTo(tag2.toString()) })

        tagItemList = ArrayList(tags.map { tag ->
            var check = false
            if(tagList!=null)
            tagList!!.map { tagString ->
                if (tag.toString() == tagString) {
                    check = true
                }
            }
            TagItem(tag, check)
        })

        view?.replaceAdapterContent(tagItemList)
    }

    override fun attachView(view: TagContract.View) {
        super.attachView(view)
        setTagItemList()
    }

    override fun check(tagName: String, check: Boolean) {
        tagItemList.first {
            it.tag.toString() == tagName
        }.checked = check
    }

    override fun getTagList() {
        val tagStringList = tagItemList.asSequence().filter {
            it.checked == true
        }.map {
            it.tag.toString()
        }.toList()

        view?.showList( ArrayList(tagStringList))
    }


//    private val tagItems= ArrayList<TagItem>()
//    private val tagsChecked = ArrayList<TagItem>()
//    private val tagsProfile: ArrayList<TagItem> = ArrayList()
//
//    override fun attachView(view: TagContract.View) {
//        super.attachView(view)
//        getTagItems()
//    }
//
//    override fun getTagItems(): ArrayList<TagItem> {
//        val tags= Tag.values()
//        val tagsArrayList=tags.toMutableList()
//        if (tags.isNotEmpty()) {
//            tagsArrayList.sortWith(Comparator { tag1, tag2 -> tag1.toString().compareTo(tag2.toString()) })
//        }
//        for(tag in tagsArrayList){
//            tagItems.add(TagItem(tag, false))
//        }
//        return tagItems
//    }
//
//    override fun getCheckedTags(): ArrayList<TagItem> {
//        return tagsChecked
//    }
//
//    override fun setSelectedTags(bundle: Bundle?) {
//        val tagItems=getTagItems()
//        val tagsChecked = bundle!!.getSerializable(PROFILE_TAGS) as? ArrayList<TagItem>?
//
//        if(tagsChecked!=null)
//            tagItems.map { tagItem ->
//                tagsChecked.map { tagCheckedItem ->
//                    if (tagItem.tag == tagCheckedItem.tag)
//                        tagItem.checked = true
//
//                }
//            }
//    }
//
//    override fun setProfileTags(listTagItem: ArrayList<TagItem>) {
//        for(tags in listTagItem)
//            if(tags.checked)
//                tagsProfile.add(tags)
//    }
//
//    override fun getProfileTags(): ArrayList<TagItem> {
//        return tagsProfile
//    }


}