package ibmro.ishqa.selectTags

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ibmro.datanetworksource.repositories.entities.TagItem
import ibmro.ishqa.R


class TagsAdapter(private var tagItems: ArrayList<TagItem>,
                  private var contract: CheckContract) : RecyclerView.Adapter<TagViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): TagViewHolder {

        val view = LayoutInflater.from(parent!!.context)
                .inflate(R.layout.tag_checkbox, parent, false) as View
        val holder = TagViewHolder(view)
        holder.onCreate {

            val tagSelected = tagItems[holder.layoutPosition]

            tagSelected.checked = !tagSelected.checked
            this.notifyDataSetChanged()


            contract.check(tagSelected.tag.toString(), tagSelected.checked)

//            if (!tagItems[holder.layoutPosition].checked) {
//                holder.getCheckedTextView()!!.isChecked = true
//                tagItems[holder.layoutPosition].checked = true
//            } else {
//                holder.getCheckedTextView()!!.isChecked = false
//                tagItems[holder.layoutPosition].checked = false
//            }
        }
        return holder
    }


    override fun onBindViewHolder(holder: TagViewHolder?, position: Int) {
        holder?.bind(tagItems[position])
    }

    override fun getItemCount(): Int {
        return tagItems.size
    }


    fun getSelectedTags(): ArrayList<TagItem> {
        return tagItems
    }
}