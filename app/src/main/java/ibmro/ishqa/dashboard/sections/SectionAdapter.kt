package ibmro.ishqa.dashboard.sections

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import ibmro.common.androidCommon.KEY_TITLE_SECTION
import ibmro.ishqa.R
import ibmro.ishqa.dashboard.sectionVM.SectionVM
import ibmro.ishqa.sectionItemsList.SectionItemListActivity

class SectionAdapter(private var sections: List<SectionVM>) : RecyclerView.Adapter<SectionViewHolder>() {

    lateinit var context: Context

//    @Inject
//    lateinit var navigator: Navigator

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): SectionViewHolder {
        val rootView = LayoutInflater.from(parent!!.context).inflate(R.layout.section_layout, parent, false)
        context = parent.context

        val holder = SectionViewHolder(rootView)
        holder.onCreate {

            val intent = Intent(context, SectionItemListActivity::class.java)
            intent.putExtra(KEY_TITLE_SECTION, sections[holder.layoutPosition].mainTag.toString())
            context.startActivity(intent)

        }
        return holder
    }

    override fun getItemCount(): Int = sections.size

    override fun onBindViewHolder(holder: SectionViewHolder?, position: Int) {
        holder!!.onBind(sections[position], context)
    }
}

