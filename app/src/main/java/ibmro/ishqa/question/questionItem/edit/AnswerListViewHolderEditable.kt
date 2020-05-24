package ibmro.ishqa.question.questionItem.edit

import android.content.Context
import android.support.design.widget.TextInputEditText
import android.support.v7.app.AlertDialog
import android.support.v7.widget.AppCompatCheckBox
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.daimajia.swipe.SwipeLayout
import ibmro.common.extension.clickOn
import ibmro.common.extension.stringToEditable
import ibmro.ishqa.R
import ibmro.ishqa.question.questionItem.QuestionPersistent.answerList

class AnswerListViewHolderEditable(itemView: View, private var context: Context) : RecyclerView.ViewHolder(itemView) {

    private val answerTextView: TextInputEditText
    private val deleteButton: TextView
    private val trueButton: TextView
    private val layout: SwipeLayout
    private val checkBox: AppCompatCheckBox


    init {
        checkBox = itemView.findViewById(R.id.answer_check_box)
        answerTextView = itemView.findViewById(R.id.answer_content)
        deleteButton = itemView.findViewById(R.id.yes_btn_delete_answer)
        trueButton = itemView.findViewById(R.id.add_question_set_true)
        layout = itemView.findViewById(R.id.answer_layout)
    }

    fun onBind() {
        answerTextView.text = stringToEditable(answerList[layoutPosition].answerContent)
        editTextListener()

        setTrueButton()
        checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
            answerList[layoutPosition].correct = !answerList[layoutPosition].correct
            setTrueButton()
        }
    }

    fun onCreate(onClick: (View) -> Unit) {
        deleteButton.setOnClickListener(onClick)
    }

    private fun setAnswerContent() {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(context.getString(R.string.set_answer_content))
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_set_question_content, null)
        val dialogEditText = view.findViewById<EditText>(R.id.dialog_edit_text)

        if (answerList[layoutPosition].answerContent.isNotBlank()) {
            dialogEditText.text = Editable.Factory.getInstance().newEditable(answerTextView.text.toString())
        }
        builder.setView(view)

        builder.setPositiveButton(android.R.string.ok) { dialog, p1 ->
            val questionContent = dialogEditText.text
            var isValid = true

            if (questionContent.isBlank()) {
                dialogEditText.error = context.getString(R.string.validation_empty)
                isValid = false
            }

            if (isValid) {
                answerList[layoutPosition].answerContent = questionContent.toString()
                answerTextView.text = questionContent
                if (questionContent.length > 500) {

                }
            }
            if (isValid)
                dialog.dismiss()
        }
        builder.setNegativeButton(android.R.string.cancel) { dialog, p1 ->
            dialog.cancel()
        }

        builder.show()
    }


    private fun setTrueButton() {
        if (answerList[layoutPosition].correct) {
            checkBox.isChecked = true
            layout.setBackgroundResource(R.drawable.background_correct_answer)
        } else {
            checkBox.isChecked = false
            layout.setBackgroundResource(R.drawable.background_wrong_answer)
        }
    }

    fun editTextListener() {
        answerTextView.onFocusChangeListener = object : View.OnFocusChangeListener {
            override fun onFocusChange(v: View?, hasFocus: Boolean) {
                if (hasFocus) {
                    setAnswerContent()
                }
            }
        }
        answerTextView.clickOn { setAnswerContent() }
        answerTextView.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                answerList[layoutPosition].answerContent = s.toString()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                answerList[layoutPosition].answerContent = s.toString()
            }

        })

    }

}