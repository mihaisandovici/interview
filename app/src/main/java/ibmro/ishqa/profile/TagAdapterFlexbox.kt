package ibmro.ishqa.profile



import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView

import android.widget.TextView
import ibmro.ishqa.R


class TagAdapterFlexbox(private val tags: ArrayList<String?>, private val closable: Boolean) : RecyclerView.Adapter<TagAdapterFlexbox.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): TagAdapterFlexbox.ViewHolder {

        val view = LayoutInflater.from(parent!!.context)
                .inflate(R.layout.tags_layout, parent, false) as View

        val holder = ViewHolder(view,closable)
        if(closable)
        holder.onCreate {
            tags.removeAt(holder.layoutPosition)
            notifyItemRemoved(holder.layoutPosition)
            this.notifyDataSetChanged()
        }

        return holder
    }


    override fun onBindViewHolder(holder: TagAdapterFlexbox.ViewHolder?, position: Int) {
        holder?.bind(tags[position])
    }

    override fun getItemCount(): Int {

            return tags.size

    }


    class ViewHolder(itemView: View,private val closable: Boolean) : RecyclerView.ViewHolder(itemView) {

        private var tagText: TextView? = null
        private var tagImageView :ImageView?=null
        private var closeTag : ImageView?=null

        init {
            tagText = itemView.findViewById(R.id.tagTextView)
            tagImageView = itemView.findViewById(R.id.tagImageView)
            closeTag = itemView.findViewById(R.id.closeTag)
        }

        fun bind(tagItem: String?) {
            tagText!!.text = tagItem
            if(!closable)
                closeTag!!.visibility=View.GONE
           // tagImageView!!.setImageResource(tagItem.tag.getImage())
        }


        fun onCreate(onClick: (View) -> Unit){
            closeTag!!.setOnClickListener(onClick)
        }

    }


}