package ibmro.ishqa.interviewer.interviewersList

import android.support.v7.widget.AppCompatTextView
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import ibmro.common.extension.setInvisible
import ibmro.datanetworksource.repositories.entities.databaseModels.responseModel.InterviewerResponse
import ibmro.ishqa.R

class SelectInterviewersViewHolder(itemView: View,
                                   private var checkAbleList: List<Int>?,
                                   private var checkable: Boolean) : RecyclerView.ViewHolder(itemView) {

    private var checkBox: CheckBox
    private var name: AppCompatTextView
    private var note: AppCompatTextView
    private var edit: TextView
    private var delete: TextView

    init {
        delete = itemView.findViewById(R.id.delete_interviewer_btn)
        edit = itemView.findViewById(R.id.edit_interviewer_btn)
        checkBox = itemView.findViewById(R.id.checkBox)
        name = itemView.findViewById(R.id.name)
        note = itemView.findViewById(R.id.note)
    }


    fun onCheck(onCheck: (View, Boolean) -> Unit) {
        if (checkable)
            checkBox.setOnCheckedChangeListener(onCheck)
        else
            checkBox.setInvisible()
    }

    fun edit(onClick: (View) -> Unit) {
        edit.setOnClickListener(onClick)
    }

    fun delete(onClick: (View) -> Unit) {
        delete.setOnClickListener(onClick)
    }

    fun onBind(interviewer: InterviewerResponse) {
        name.text = "${interviewer.firstName} ${interviewer.lastName}"
        note.text = interviewer.note

        if (checkable)
            checkBox.isChecked = checkAbleList != null && checkAbleList!!.contains(interviewer.id)
    }

    fun viewProfile(onCLick: (View) -> Unit) {
        name.setOnClickListener(onCLick)
    }

}