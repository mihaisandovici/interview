package ibmro.ishqa.interview.interviewsList.tagsRecycler

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import ibmro.datanetworksource.repositories.entities.Tag
import ibmro.ishqa.R

class TagAdapterFlexBox(private var tagList: List<Tag?>) : RecyclerView.Adapter<TagViewHolderFlexBox>() {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): TagViewHolderFlexBox {
        val rootView = LayoutInflater.from(parent!!.context).inflate(R.layout.tags_layout, parent, false)

        val holder = TagViewHolderFlexBox(rootView)
        holder.onCreate()

        return holder
    }

    override fun getItemCount(): Int = tagList.size

    override fun onBindViewHolder(holder: TagViewHolderFlexBox?, position: Int) {
        holder!!.onBind(tagList[position])
    }
}