package ibmro.ishqa.dashboard.items

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import ibmro.datanetworksource.repositories.entities.Tag
import ibmro.ishqa.R
import ibmro.ishqa.candidate.candidateList.CandidateListActivity
import ibmro.ishqa.dashboard.sectionVM.SectionItemVM
import ibmro.ishqa.interview.interviewsList.InterviewsListActivity
import ibmro.ishqa.interviewer.interviewersList.INTERVIEWERS_CHECKABLE
import ibmro.ishqa.interviewer.interviewersList.SelectInterviewersActivity
import ibmro.ishqa.question.questionList.ITEM_TAG
import ibmro.ishqa.question.questionList.MAIN_TAG
import ibmro.ishqa.question.questionList.QuestionListActivity

class ItemAdapter(private var items: List<SectionItemVM>) : RecyclerView.Adapter<ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ItemViewHolder {
        val rootView = LayoutInflater.from(parent!!.context).inflate(R.layout.item_layout, parent, false)

        val holder = ItemViewHolder(rootView)
        holder.onCreate {
            val item = items[holder.layoutPosition]
            goActivity(item.itemMainTag, item.itemTag, parent.context)

        }

        return holder
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.onBind(items[position])
    }

    private fun goActivity(mainTag: Tag, itemTag: Tag, context: Context) {
        val stringMainTag = mainTag.toString()
        val stringItemTag = itemTag.toString()
        var intent: Intent? = null

        when (mainTag) {
            Tag.Topics -> intent = Intent(context, QuestionListActivity::class.java)
            Tag.ISH -> intent = Intent(context, QuestionListActivity::class.java)
            Tag.Learn -> Toast.makeText(context, "Go Learn Item", Toast.LENGTH_LONG).show()
            Tag.ManageInterviews -> {
                when (itemTag) {
                    Tag.Candidate -> intent = Intent(context, CandidateListActivity::class.java)
                    Tag.Interviews -> intent = Intent(context, InterviewsListActivity::class.java)
                    Tag.Interviewers -> {
                        intent = Intent(context, SelectInterviewersActivity::class.java)
                        intent.putExtra(INTERVIEWERS_CHECKABLE, false)
                    }
                    else -> Toast.makeText(context, context.getString(R.string.error_mesagge), Toast.LENGTH_LONG).show()
                }
            }
            else -> Toast.makeText(context, context.getString(R.string.error_mesagge), Toast.LENGTH_LONG).show()
        }

        if (intent != null) {
            intent.run {
                putExtra(MAIN_TAG, stringMainTag)
                putExtra(ITEM_TAG, stringItemTag)
            }
            context.startActivity(intent)
        }
//        else
//            Toast.makeText(context, context.getString(R.string.error_mesagge), Toast.LENGTH_LONG).show()
    }

}