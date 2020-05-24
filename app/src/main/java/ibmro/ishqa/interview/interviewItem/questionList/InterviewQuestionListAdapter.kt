package ibmro.ishqa.interview.interviewItem.questionList

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import ibmro.ishqa.R
import ibmro.ishqa.interview.interviewItem.QuestionModel

class InterviewQuestionListAdapter(
        private var context: Context,
        private var questionList: ArrayList<QuestionModel>,
        private var haveNote: Boolean
) : RecyclerView.Adapter<InterviewQuestionListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): InterviewQuestionListViewHolder {
        val rootView = LayoutInflater.from(parent!!.context).inflate(R.layout.item_question_of_interview, parent, false)
        val holder = InterviewQuestionListViewHolder(rootView, haveNote, context)

        holder.onClick {

        }
        return holder
    }

    override fun getItemCount(): Int = questionList.size

    override fun onBindViewHolder(holder: InterviewQuestionListViewHolder?, position: Int) {
        holder!!.onBind(questionList[position])
    }

}