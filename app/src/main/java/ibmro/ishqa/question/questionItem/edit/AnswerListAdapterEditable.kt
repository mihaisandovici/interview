package ibmro.ishqa.question.questionItem.edit

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import ibmro.ishqa.R
import ibmro.ishqa.question.questionItem.QuestionPersistent.answerList

class AnswerListAdapterEditable(private var context: Context) : RecyclerView.Adapter<AnswerListViewHolderEditable>() {


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): AnswerListViewHolderEditable {
        val rootView = LayoutInflater.from(parent!!.context).inflate(R.layout.item_answer, parent, false)

        val holder = AnswerListViewHolderEditable(rootView, context)

        holder.onCreate {
            Toast.makeText(context, context.getString(R.string.answer_deleted), Toast.LENGTH_LONG).show()
            answerList.removeAt(holder.layoutPosition)
            this.notifyDataSetChanged()
        }

        return holder
    }

    override fun getItemCount(): Int = answerList.size

    override fun onBindViewHolder(holder: AnswerListViewHolderEditable?, position: Int) =
            holder!!.onBind()
}