package ibmro.ishqa.interview.interviewsList

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import ibmro.common.extension.setInvisible
import ibmro.common.extension.setVisible
import ibmro.datanetworksource.repositories.entities.Tag
import ibmro.datanetworksource.repositories.entities.viewModels.InterviewViewModel
import ibmro.ishqa.R
import ibmro.ishqa.interview.interviewsList.tagsRecycler.TagAdapterFlexBox
import java.util.*

class InterviewsListViewHolder(itemView: View, private var context: Context) : RecyclerView.ViewHolder(itemView) {

    private var date: TextView
    private var deleteInterview: TextView
    private var duplicateInterview: TextView
    private var candidateName: TextView
    private var tagList: RecyclerView
    private var layout: ConstraintLayout
    private var image: ImageView
    private var playInterview: ImageView
    private var edit: TextView


    init {
        edit = itemView.findViewById(R.id.edit_interview_btn)
        playInterview = itemView.findViewById(R.id.play_interview)
        image = itemView.findViewById(R.id.imageView)
        layout = itemView.findViewById(R.id.interview_layout)
        date = itemView.findViewById(R.id.interview_date)
        candidateName = itemView.findViewById(R.id.interview_candidate_name)
        tagList = itemView.findViewById(R.id.interview_item_tag_list)
        deleteInterview = itemView.findViewById(R.id.delete_interview_btn)
        duplicateInterview = itemView.findViewById(R.id.duplicate_interview_btn)
    }


    fun onBind(interview: InterviewViewModel) {
        candidateName.text = interview.candidate!!.fullName
        date.text = interview.date
        if (dateIsAfter(interview.date)) {
            image.setInvisible()
            playInterview.setVisible()
        } else {
            playInterview.setInvisible()
            image.setVisible()
        }

        replaceTagListContent(interview.tags)
    }

    fun onViewBtn(onClick: (View) -> Unit) {
        layout.setOnClickListener(onClick)
    }

    fun deleteBtn(onClick: (View) -> Unit) {
        deleteInterview.setOnClickListener(onClick)
    }

    fun duplicate(onClick: (View) -> Unit) {
        duplicateInterview.setOnClickListener(onClick)
    }

    fun edit(onClick: (View) -> Unit) {
        edit.setOnClickListener(onClick)
    }

    fun replaceTagListContent(tags: List<Tag?>) {
        val flexboyLayoutManager = FlexboxLayoutManager(context).apply {
            flexWrap = FlexWrap.WRAP
            flexDirection = FlexDirection.ROW_REVERSE
            alignItems = AlignItems.STRETCH
        }

        tagList.apply {
            layoutManager = flexboyLayoutManager
            adapter = TagAdapterFlexBox(tags)
        }
    }

    fun playInterview(onClick: (View) -> Unit) {
        playInterview.setOnClickListener(onClick)
    }

    private fun dateIsAfter(date: String): Boolean {

        val list = date.split("[/ :]".toRegex())
        val mounth = list[0].toInt()
        val day = list[1].toInt()
        val year = list[2].toInt()
        val hour = list[3].toInt()
        val minute = list[4].toInt()

        val now = Calendar.getInstance()
        val dateCalendar = Calendar.getInstance()
        dateCalendar.set(Calendar.YEAR, year)
        dateCalendar.set(Calendar.MONTH, mounth - 1)
        dateCalendar.set(Calendar.DAY_OF_MONTH, day)
        dateCalendar.set(Calendar.HOUR_OF_DAY, hour)
        dateCalendar.set(Calendar.MINUTE, minute)
        dateCalendar.set(Calendar.SECOND, 0)

        return dateCalendar.time.after(now.time)
    }
}