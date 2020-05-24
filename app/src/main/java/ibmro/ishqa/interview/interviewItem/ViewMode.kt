package ibmro.ishqa.interview.interviewItem

enum class ViewMode(value: Int) {
    VIEW(0),
    DUPLICATE(1),
    EDIT(2),
    ADD(3);


    companion object {
        fun getViewMode(value: Int): ViewMode {
            when (value) {
                1 -> return DUPLICATE
                2 -> return EDIT
                3 -> return ADD
                else -> return VIEW
            }
        }
    }

    fun isEditable(): Boolean {
        when (this) {
            VIEW -> return false
            DUPLICATE -> return true
            EDIT -> return true
            ADD -> return true
        }
    }
}