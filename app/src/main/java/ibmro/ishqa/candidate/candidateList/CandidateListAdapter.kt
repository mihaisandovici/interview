package ibmro.ishqa.candidate.candidateList

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import ibmro.common.androidCommon.KEY_CANDIDATE_ID
import ibmro.common.androidCommon.KEY_CANDIDATE_VIEW
import ibmro.common.extension.confirmDialogOnClick
import ibmro.datanetworksource.repositories.entities.databaseModels.responseModel.CandidateResponse
import ibmro.ishqa.R
import ibmro.ishqa.candidate.candidateItem.CandidateActivity

class CandidateListAdapter(private var candidates: ArrayList<CandidateResponse>,
                           private var  deleteCandidateContract: CandidateDeleteContract,
                           private var checkable: Boolean
                         ) : RecyclerView.Adapter<CandidateListViewHolder>() {

    var selectedPosition = -1
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): CandidateListViewHolder {
        val rootView = LayoutInflater.from(parent!!.context).inflate(R.layout.item_candidate, parent, false)
        val holder = CandidateListViewHolder(rootView,checkable)

        holder.onCreateCheck {
           // selectedPosition=holder.layoutPosition
           // val returnIntent = Intent(parent.context,InterviewActivity::class.java)
            val candidateSelected=CandidateViewModel(candidates[holder.layoutPosition].id,
                    candidates[holder.layoutPosition].firstName,
                    candidates[holder.layoutPosition].lastName,
                    candidates[holder.layoutPosition].phoneNr,
                    candidates[holder.layoutPosition].candidateCV,
                    candidates[holder.layoutPosition].technicalLevel,
                    candidates[holder.layoutPosition].prefTechnologies,
                    candidates[holder.layoutPosition].email,
                    candidates[holder.layoutPosition].tags)
            deleteCandidateContract.selectCandidate(candidateSelected)

        }

        holder.check{ view,isChecked->
                selectedPosition=holder.layoutPosition
        }
        holder.editBtn {
            startCandidateActivity(candidates[holder.layoutPosition].id, true, parent.context)
        }
        holder.candidateLayout {
            startCandidateActivity(candidates[holder.layoutPosition].id, false, parent.context)
        }

        holder.deleteCandidate {
            val id = candidates[holder.layoutPosition].id
            it.confirmDialogOnClick(parent.context, R.string.delete_candidate) {
                candidates.removeAt(holder.layoutPosition)
            }
            deleteCandidateContract.delete(id)
        }

        return holder
    }

    override fun getItemCount(): Int = candidates.size

    override fun onBindViewHolder(holder: CandidateListViewHolder?, position: Int) {

        val candidate = candidates[position]
        holder!!.onBind(candidate.firstName, candidate.lastName, candidate.tags)//, imageResource)
    }

    private fun startCandidateActivity(candidateId: Int, editable: Boolean, context: Context) {
        val intent = Intent(context, CandidateActivity::class.java)
                .putExtra(KEY_CANDIDATE_ID, candidateId)
                .putExtra(KEY_CANDIDATE_VIEW, editable)
        context.startActivity(intent)
    }

}
const val SELECTED_CANDIDATE="SELECTED_CANDIDATE"