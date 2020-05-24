package ibmro.ishqa.question.questionList

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import ibmro.common.androidCommon.KEY_ID_QUESTION
import ibmro.common.androidCommon.KEY_QUESTION_VIEW
import ibmro.common.extension.confirmDialogOnClick
import ibmro.datanetworksource.repositories.entities.databaseModels.responseModel.QuestionResponse
import ibmro.ishqa.R
import ibmro.ishqa.question.questionItem.QuestionActivity

class QuestionListAdapter(private var questionList: ArrayList<QuestionResponse>,
                          private var contract: DeleteContract,
                          private var imageResource: Int,
                          private var checkable: Boolean,
                          private var checkableList: List<Int>?
) : RecyclerView.Adapter<QuestionListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): QuestionListViewHolder {
        val rootView = LayoutInflater.from(parent!!.context).inflate(R.layout.item_question, parent, false)
        val holder = QuestionListViewHolder(rootView, checkable, parent.context, checkableList)

        holder.onCreateCheck {
            //cand e check ce sa faca
        }

        holder.editBtn {
            goQuestionActivity(questionList[holder.layoutPosition].id, true, parent.context)
        }
        holder.questionBtn {
            goQuestionActivity(questionList[holder.layoutPosition].id, false, parent.context)
        }

        holder.check { view, isCheck ->
            contract.check(questionList[holder.layoutPosition].id, isCheck)
        }

        holder.deleteQuestion {
            val id = questionList[holder.layoutPosition].id
            it.confirmDialogOnClick(parent.context, R.string.delete_question_q, {
                questionList.removeAt(holder.layoutPosition)
            })
            contract.delete(id)
        }

        return holder
    }

    override fun getItemCount(): Int = questionList.size

    override fun onBindViewHolder(holder: QuestionListViewHolder?, position: Int) {
        val question = questionList[position]
        holder!!.onBind(question.questionContent, question.tags, question.id, imageResource)
    }

    private fun goQuestionActivity(questionId: Int, editable: Boolean, context: Context) {
        val intent = Intent(context, QuestionActivity::class.java)
                .putExtra(KEY_ID_QUESTION, questionId)
                .putExtra(KEY_QUESTION_VIEW, editable)
        context.startActivity(intent)
    }

}