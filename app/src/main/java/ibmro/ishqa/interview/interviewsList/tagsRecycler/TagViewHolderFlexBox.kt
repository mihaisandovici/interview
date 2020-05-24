package ibmro.ishqa.interview.interviewsList.tagsRecycler

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import ibmro.datanetworksource.repositories.entities.Tag
import ibmro.ishqa.R
import ibmro.ishqa.dashboard.sectionVM.getImage

class TagViewHolderFlexBox(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var tagText: TextView
    private var tagImageView: ImageView
    private var closeTag: ImageView

    init {
        tagText = itemView.findViewById(R.id.tagTextView)
        tagImageView = itemView.findViewById(R.id.tagImageView)
        closeTag = itemView.findViewById(R.id.closeTag)
    }

    fun onCreate() {
        closeTag.visibility = View.GONE
    }


    fun onBind(tag: Tag?) {
        tagImageView.setImageResource(tag!!.getImage())
        tagText.text = tag.toString()
    }

}