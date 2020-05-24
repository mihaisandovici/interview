package ibmro.common.extension

import android.app.AlertDialog
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText

fun View.clickOn(onClick: (View) -> Unit) {
    this.setOnClickListener(onClick)
}

fun View.confirmDialogOnClick(context: Context, messageRes: Int, action: () -> Unit) {
    this.clickOn {
        val builder = AlertDialog.Builder(context)
        builder.setMessage(messageRes)
                .setPositiveButton(android.R.string.ok) { dialog, p1 ->
                    action()
                    dialog.dismiss()
                }
                .setNegativeButton(android.R.string.no) { dialog, which ->
                    dialog.dismiss()
                }
        builder.show()
    }
}

fun stringToEditable(string: String): Editable =
        Editable.Factory.getInstance().newEditable(string)

fun View.setInvisible() {
    this.visibility = View.GONE
}

fun View.setVisible() {
    this.visibility = View.VISIBLE
}

/*
fun EditText.addTextChanged() : String{
    var text : String=""
    this.addTextChangedListener(object : TextWatcher{
        override fun afterTextChanged(s: Editable?) {
           text=s.toString()
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            text=s.toString()
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            text=s.toString()
        }

    })
    return text
}*/

fun EditText.onChange(cb: (String) -> Unit) {
    this.addTextChangedListener(object: TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            if(s!=null)
            cb(s.toString())
         else cb("")}
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    })
}