package ibmro.ishqa.question.questionList

interface DeleteContract {

    fun delete(id: Int)

    fun check(id: Int, isCheck: Boolean)
}