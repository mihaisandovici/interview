package ibmro.ishqa.candidate.candidateList

import java.io.Serializable

data class CandidateViewModel (
        var id : Int,
        var firstname: String,
        var lastname : String,
        val phoneNr: Long,
        val candidateCV: String?,
        val technicalLvl : String?,
        val prefTechnologies: List<String>,
        val email: String,
        val tags: List<String>
) : Serializable