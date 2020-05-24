package ibmro.ishqa.candidate.candidateList

interface CandidateDeleteContract {
    fun delete(id : Int)

    fun selectCandidate(candidateViewModel: CandidateViewModel)
}