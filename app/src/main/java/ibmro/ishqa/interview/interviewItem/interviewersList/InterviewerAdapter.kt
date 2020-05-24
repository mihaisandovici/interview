package ibmro.ishqa.interview.interviewItem.interviewersList

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import ibmro.ishqa.R
import ibmro.ishqa.interview.interviewItem.InterviewersModel

class InterviewerAdapter(private var interviewer: List<InterviewersModel>,
                         private val contract: InterviewerContract) : RecyclerView.Adapter<InterviewerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): InterviewerViewHolder {
        val rootView = LayoutInflater.from(parent!!.context).inflate(R.layout.interviewer_layout, parent, false)
        val holder = InterviewerViewHolder(rootView)

        holder.onCreate {
            contract.showProfile(interviewer[holder.layoutPosition])
        }
        return holder
    }

    override fun getItemCount(): Int = interviewer.size

    override fun onBindViewHolder(holder: InterviewerViewHolder?, position: Int) {
        holder!!.onBind(interviewer[position].fullName)
    }
}