package ibmro.ishqa.sectionItemsList

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import ibmro.ishqa.R
import ibmro.ishqa.dashboard.sectionVM.SectionItemVM
import ibmro.ishqa.question.questionList.ITEM_TAG
import ibmro.ishqa.question.questionList.MAIN_TAG
import ibmro.ishqa.question.questionList.QuestionListActivity

class SectionItemListAdapter(private var itemList: List<SectionItemVM>) : RecyclerView.Adapter<SectionItemListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): SectionItemListViewHolder {
        val rootview = LayoutInflater.from(parent!!.context).inflate(R.layout.viewmore_item_list, parent, false)
        val holder = SectionItemListViewHolder(rootview)

        holder.onCreate {
            val itemTag = itemList[holder.layoutPosition].itemTag.toString()
            val mainTag = itemList[holder.layoutPosition].itemMainTag.toString()
            val intent = Intent(parent.context, QuestionListActivity::class.java)
            intent.putExtra(ITEM_TAG, itemTag)
            intent.putExtra(MAIN_TAG, mainTag)
            parent.context.startActivity(intent)
        }

        return holder
    }

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holderSection: SectionItemListViewHolder?, position: Int) {
        holderSection!!.onBind(itemList[position].titleItem, itemList[position].imageResource)
    }
}