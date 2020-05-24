package ibmro.ishqa.interview.interviewItem.questionList

import android.content.Context
import android.support.v7.widget.AppCompatTextView
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import ibmro.common.extension.setInvisible
import ibmro.ishqa.R
import ibmro.ishqa.interview.interviewItem.QuestionModel
import ibmro.ishqa.interview.playInterview.ResultEnum

class InterviewQuestionListViewHolder(itemView: View, private var haveNote: Boolean,
                                      private var context: Context) : RecyclerView.ViewHolder(itemView) {

    private var content: AppCompatTextView
    private var result: AppCompatTextView
    private var count: TextView

    init {
        count = itemView.findViewById(R.id.question_count)
        content = itemView.findViewById(R.id.interview_question_content)
        result = itemView.findViewById(R.id.interview_question_result)
    }

    fun onBind(question: QuestionModel) {
        count.text = " ${layoutPosition + 1}."
        content.text = question.questionContent
        if (haveNote) {
            var resutEnum = ResultEnum.getResultForDatabase(question.answer)
            result.text = resutEnum.toString(context)
            result.setBackgroundColor(resutEnum.getColor(context))
        } else
            result.setInvisible()
    }

    fun onClick(onCLick: (View) -> Unit) {

    }
}