package ibmro.ishqa.selectTags

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.CheckedTextView
import ibmro.datanetworksource.repositories.entities.TagItem
import ibmro.ishqa.R

class TagViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var mCheckedTextView: CheckedTextView

    init {
        mCheckedTextView = itemView.findViewById(R.id.checked_text_view)
    }

    fun bind(tagItem: TagItem) {

        mCheckedTextView.isChecked = tagItem.checked
        mCheckedTextView.text = tagItem.tag.toString()
    }

    fun onCreate(onClick: (View) -> Unit) {
        itemView!!.setOnClickListener(onClick)
    }

    fun getCheckedTextView(): CheckedTextView? {
        return mCheckedTextView
    }

}