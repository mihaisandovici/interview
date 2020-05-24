package ibmro.ishqa.sign

enum class RoleEnum(value: Int) {
    ADMINISTRATOR(0),
    INTERVIEWER(1),
    ANONYM_USER(2);

    override fun toString(): String {
        return when (this) {
            ADMINISTRATOR -> "ADMINISTRATOR"
            INTERVIEWER -> "INTERVIEWER"
            ANONYM_USER -> "ANONYM_USER"
        }
    }

    companion object {

        fun getRoleByString(role: String): RoleEnum {
            return when (role) {
                "ADMINISTRATOR" -> ADMINISTRATOR
                "INTERVIEWER" -> INTERVIEWER
                else -> ANONYM_USER
            }
        }
    }
}