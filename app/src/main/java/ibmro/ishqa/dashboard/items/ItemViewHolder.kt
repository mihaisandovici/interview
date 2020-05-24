package ibmro.ishqa.dashboard.items

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import ibmro.ishqa.R
import ibmro.ishqa.dashboard.sectionVM.SectionItemVM

class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val title: TextView
    private val image: ImageView
    private val cardView: CardView

    init {
        title = itemView.findViewById(R.id.title_item)
        image = itemView.findViewById(R.id.item_image)
        cardView = itemView.findViewById(R.id.item_card_view)
    }

    fun onBind(item: SectionItemVM) {
        title.text = item.titleItem
        image.setImageResource(item.imageResource)
    }

    fun onCreate(onclick: (View) -> Unit) {
        cardView.setOnClickListener(onclick)
    }
}