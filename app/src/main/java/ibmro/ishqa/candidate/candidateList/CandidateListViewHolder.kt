package ibmro.ishqa.candidate.candidateList

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import ibmro.common.extension.setInvisible
import ibmro.common.extension.setVisible
import ibmro.ishqa.R


class CandidateListViewHolder(itemView : View, private val checkable: Boolean
                             ) : RecyclerView.ViewHolder(itemView) {
    private var id=-1
    private val firstName : TextView
    private val lastName : TextView
    private val tags: TextView
    private val imageCandidate: ImageView
    private val layoutCandidate: LinearLayout
    private val edit: TextView
    private val delete: TextView
    private var selectCandidate : ImageView



    init{
        selectCandidate=itemView.findViewById(R.id.selectCandidate)
        firstName=itemView.findViewById(R.id.candidate_firstName)
        lastName=itemView.findViewById(R.id.candidate_lastName)
        tags = itemView.findViewById(R.id.candidate_tags_list)
        imageCandidate = itemView.findViewById(R.id.image_candidate_item)
        layoutCandidate = itemView.findViewById(R.id.candidate_layout_item)
        edit = itemView.findViewById(R.id.edit_candidate_btn)
        delete = itemView.findViewById(R.id.delete_candidate_list)
    }

    fun onBind(firstName: String, lastName: String, tagList: List<String>) {//, imageResource: Int) {
        this.firstName.text = firstName
        this.lastName.text = lastName
        //imageCandidate.setImageResource(imageResource)

        val tags = StringBuilder(" ")

        tagList.map { tagname ->
            tags.append("#$tagname, ")
        }

        //tags.replace(tags.lastIndex - 1, tags.lastIndex, "")

        this.tags.text = tags
    }

    fun onCreateCheck(onClick: (View) -> Unit){
        if (!checkable) {
            selectCandidate.setInvisible()
        } else
            selectCandidate.setVisible()

        selectCandidate.setOnClickListener(onClick)
    }

    fun check(onCheck: (View, Boolean) -> Unit) {
        //checkBox.setOnCheckedChangeListener(onCheck)
    }

    fun editBtn(onClick: (View) -> Unit) {
        edit.setOnClickListener(onClick)
    }

    fun candidateLayout(onClick: (View) -> Unit) {
        layoutCandidate.setOnClickListener(onClick)
    }

    fun deleteCandidate(onClick: (View) -> Unit) {
        delete.setOnClickListener(onClick)
    }
}