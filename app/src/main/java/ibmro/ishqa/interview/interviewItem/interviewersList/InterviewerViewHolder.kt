package ibmro.ishqa.interview.interviewItem.interviewersList

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import ibmro.ishqa.R

class InterviewerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var name: TextView

    init {
        name = itemView.findViewById(R.id.name_interviewer)
    }

    fun onBind(intName: String) {
        name.text = intName
    }

    fun onCreate(onClick: (View) -> Unit) {
        name.setOnClickListener(onClick)
    }
}