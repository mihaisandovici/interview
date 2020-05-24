package ibmro.ishqa.question.questionList

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import ibmro.common.extension.setInvisible
import ibmro.common.extension.setVisible
import ibmro.ishqa.R
import ibmro.ishqa.dashboard.sectionVM.getImage
import ibmro.ishqa.interview.interviewsList.tagsRecycler.TagListMapper

class QuestionListViewHolder(itemView: View,
                             private var checkable: Boolean,
                             private var context: Context,
                             private var checkableList: List<Int>?) : RecyclerView.ViewHolder(itemView) {

    private var checkBox: CheckBox
    private var id: Int = -1
    private val title: TextView
    private val tags: TextView
    private val edit: TextView
    private val layoutQuestion: LinearLayout
    private val delete: TextView
    private val imageQuestion: ImageView

    init {
        checkBox = itemView.findViewById(R.id.checkbox_question)
        imageQuestion = itemView.findViewById(R.id.image_question_item)
        delete = itemView.findViewById(R.id.delete_question_list)
        layoutQuestion = itemView.findViewById(R.id.question_layout_item)
        title = itemView.findViewById(R.id.question_title_list)
        tags = itemView.findViewById(R.id.queston_tags_list)
        edit = itemView.findViewById(R.id.edit_question_btn)
    }

    fun onBind(titleQuestion: String, tagList: List<String>, id: Int, imageResource: Int) {
        this.id = id
        title.text = titleQuestion

        val tagsList = TagListMapper().map(tagList)

        if (checkable && tagsList.isNotEmpty()) {
            val imageRes = tagsList.first()!!.getImage()
            imageQuestion.setImageResource(imageRes)
        } else {
            imageQuestion.setImageResource(imageResource)
        }

        val tags = StringBuilder(" ")

        tagList.map { tagname ->
            tags.append("#$tagname, ")
        }

        tags.replace(tags.lastIndex - 1, tags.lastIndex, "")

        this.tags.text = tags

        if (checkableList != null && checkableList!!.contains(id)) {
            checkBox.isChecked = true
        } else
            checkBox.isChecked = false

//        replaceTagListContent(tags)
    }

//    private fun replaceTagListContent(tagList: List<Tag?>) {
//        val flexboyLayoutManager = FlexboxLayoutManager(context).apply {
//            flexWrap = FlexWrap.WRAP
//            flexDirection = FlexDirection.ROW
//            alignItems = AlignItems.STRETCH
//        }
//
//        tags.apply {
//            layoutManager = flexboyLayoutManager
//            adapter = TagAdapterFlexBox(tagList)
//        }
//    }

    fun onCreateCheck(onClick: (View) -> Unit) {
        if (!checkable) {
            checkBox.setInvisible()
        } else
            checkBox.setVisible()

        checkBox.setOnClickListener(onClick)
    }

    fun editBtn(onClick: (View) -> Unit) {
        edit.setOnClickListener(onClick)
    }

    fun questionBtn(onClick: (View) -> Unit) {
        layoutQuestion.setOnClickListener(onClick)
    }

    fun deleteQuestion(onClick: (View) -> Unit) {
        delete.setOnClickListener(onClick)
    }

    fun check(onCheck: (View, Boolean) -> Unit) {
        checkBox.setOnCheckedChangeListener(onCheck)
    }

}
