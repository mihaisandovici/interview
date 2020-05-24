package ibmro.ishqa.question.questionItem.view

import android.app.AlertDialog
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import ibmro.common.extension.clickOn
import ibmro.ishqa.R
import ibmro.ishqa.question.questionItem.AnswerModel

class AnswerListViewHolderUneditable(itemView: View, private var context: Context) : RecyclerView.ViewHolder(itemView) {

    private val answerTextView: TextView
    private val layout: LinearLayout

    init {
        answerTextView = itemView.findViewById(R.id.answer_content)
        layout = itemView.findViewById(R.id.answer_layout_uneditable)
    }

    fun onBind(answer: AnswerModel) {
        answerTextView.text = answer.answerContent
        answerTextView.clickOn {
            showDialog(answer.answerContent)
        }
        setTrueButton(answer)
    }

    private fun showDialog(text: String) {
        val builder = AlertDialog.Builder(context)
                .setMessage(text)
                .create()
        builder.show()
    }


    private fun setTrueButton(answer: AnswerModel) {
        if (answer.correct) {
            layout.setBackgroundResource(R.drawable.background_correct_answer)
        } else {
            layout.setBackgroundResource(R.drawable.background_wrong_answer)
        }
    }

}