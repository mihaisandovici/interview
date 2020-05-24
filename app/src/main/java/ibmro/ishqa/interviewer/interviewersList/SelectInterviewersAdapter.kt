package ibmro.ishqa.interviewer.interviewersList

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import ibmro.datanetworksource.repositories.entities.databaseModels.responseModel.InterviewerResponse
import ibmro.ishqa.R

class SelectInterviewersAdapter(private var list: ArrayList<InterviewerResponse>,
                                private var checkList: List<Int>,
                                private var contract: InterviewersItemContract,
                                private var checkable: Boolean) : RecyclerView.Adapter<SelectInterviewersViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): SelectInterviewersViewHolder {
        val rootview = LayoutInflater.from(parent!!.context).inflate(R.layout.item_interviewers, parent, false)
        val holder = SelectInterviewersViewHolder(rootview, checkList, checkable)

        holder.delete {
            val id = list[holder.layoutPosition].id
            list.removeAt(holder.layoutPosition)
            this.notifyDataSetChanged()
            contract.delete(id)
        }

        holder.edit {
            contract.edit(list[holder.layoutPosition])
        }

        holder.onCheck { view, isCheck ->
            contract.check(list[holder.layoutPosition].id, isCheck)
        }

        holder.viewProfile {
            contract.view(list[holder.layoutPosition])
        }
        return holder
    }

    override fun getItemCount(): Int = list.size


    override fun onBindViewHolder(holder: SelectInterviewersViewHolder?, position: Int) {
        holder!!.onBind(list[position])
    }
}