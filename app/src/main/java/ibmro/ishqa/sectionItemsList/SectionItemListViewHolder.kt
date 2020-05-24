package ibmro.ishqa.sectionItemsList

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import ibmro.ishqa.R

class SectionItemListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val titleView: TextView
    private val imageView: ImageView
    private val cardView: CardView

    init {
        titleView = itemView.findViewById(R.id.viewmore_title)
        imageView = itemView.findViewById(R.id.viewmore_image)
        cardView = itemView.findViewById(R.id.cardview_item_viewmore)
    }

    fun onBind(title: String, image: Int) {
        titleView.text = title
        imageView.setImageResource(image)
    }

    fun onCreate(onClick: (View) -> Unit) {
        cardView.setOnClickListener(onClick)
    }
}