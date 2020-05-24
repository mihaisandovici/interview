package ibmro.ishqa.interview.interviewsList

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import ibmro.datanetworksource.repositories.entities.viewModels.InterviewViewModel
import ibmro.ishqa.R

class InterviewsListAdapter(private var interviews: ArrayList<InterviewViewModel>,
                            private var contract: ItemContract)
    : RecyclerView.Adapter<InterviewsListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): InterviewsListViewHolder {
        val rootView = LayoutInflater.from(parent!!.context).inflate(R.layout.item_interview, parent, false)
        val holder = InterviewsListViewHolder(rootView, parent.context)

        holder.edit {
            contract.edit(interviews[holder.layoutPosition])
        }

        holder.duplicate {
            contract.duplicate(interviews[holder.layoutPosition])
        }

        holder.deleteBtn {
            contract.delete(interviews[holder.layoutPosition].id)
            interviews.removeAt(holder.layoutPosition)
            this.notifyDataSetChanged()
        }

        holder.onViewBtn {
            contract.view(interviews[holder.layoutPosition])
        }

        holder.playInterview {
            contract.playInterview(interviews[holder.layoutPosition])
        }

        return holder
    }

    override fun getItemCount(): Int = interviews.size

    override fun onBindViewHolder(holder: InterviewsListViewHolder?, position: Int) {
        holder!!.onBind(interviews[position])
    }
}