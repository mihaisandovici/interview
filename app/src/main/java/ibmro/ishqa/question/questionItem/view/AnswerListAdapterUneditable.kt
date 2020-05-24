package ibmro.ishqa.question.questionItem.view

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import ibmro.ishqa.R
import ibmro.ishqa.question.questionItem.AnswerModel
import ibmro.ishqa.question.questionItem.QuestionPersistent

class AnswerListAdapterUneditable(private var context: Context, private var answerList: ArrayList<AnswerModel>) : RecyclerView.Adapter<AnswerListViewHolderUneditable>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): AnswerListViewHolderUneditable {
        val rootView = LayoutInflater.from(parent!!.context).inflate(R.layout.item_answer_uneditable, parent, false)

        val holder = AnswerListViewHolderUneditable(rootView, context)

        return holder
    }

    override fun getItemCount(): Int = QuestionPersistent.answerList.size

    override fun onBindViewHolder(holder: AnswerListViewHolderUneditable?, position: Int) =
            holder!!.onBind(answerList[position])

}