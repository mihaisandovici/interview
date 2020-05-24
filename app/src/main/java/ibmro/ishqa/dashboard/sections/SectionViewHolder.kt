package ibmro.ishqa.dashboard.sections

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import ibmro.ishqa.R
import ibmro.ishqa.dashboard.items.ItemAdapter
import ibmro.ishqa.dashboard.sectionVM.SectionItemVM
import ibmro.ishqa.dashboard.sectionVM.SectionVM

class SectionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val title: TextView
    private val description: TextView
    private val noItems: TextView
    private val sectionRecycler: RecyclerView
    private val viewMoreBtn: TextView

    init {
        viewMoreBtn = itemView.findViewById(R.id.view_more_section)
        title = itemView.findViewById(R.id.title_section)
        description = itemView.findViewById(R.id.describe_section)
        sectionRecycler = itemView.findViewById(R.id.section_recycler)
        noItems = itemView.findViewById(R.id.section_noitems)

    }

    fun onCreate(onClick: (View) -> Unit) {
        viewMoreBtn.setOnClickListener(onClick)

    }

    fun onBind(section: SectionVM, context: Context) {
        title.text = section.titleSection
        description.text = section.descriptionSection

        if (section.listSectionItem.isEmpty()) {
            noItems.visibility = View.VISIBLE
        } else {
            setupRecycler(section.listSectionItem, context)
        }
    }

    private fun setupRecycler(items: List<SectionItemVM>, context: Context) {
        sectionRecycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        sectionRecycler.adapter = ItemAdapter(items)
        sectionRecycler.setHasFixedSize(true)
    }
}