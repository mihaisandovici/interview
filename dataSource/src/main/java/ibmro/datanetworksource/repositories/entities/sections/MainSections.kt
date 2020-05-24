package ibmro.datanetworksource.repositories.entities.sections

import ibmro.datanetworksource.repositories.entities.Tag

internal enum class MainSections(val value: Int) {
    Topics(1),
    ISH(2),
    Learn(3),
    ManageInterviews(4);

    companion object {
        fun getItemByString(itemKey: String): MainSections? {
            when (itemKey) {
                Topics.toString() -> return Topics
                ISH.toString() -> return ISH
                Learn.toString() -> return Learn
                ManageInterviews.toString() -> return ManageInterviews

                else -> return null
            }
        }

    }

    override fun toString(): String {
        return when (this) {
            Topics -> Tag.Topics.toString();
            ISH -> Tag.ISH.toString();
            Learn -> Tag.Learn.toString();
            ManageInterviews -> Tag.ManageInterviews.toString();
        }
    }

    fun getTag(): Tag {
        return when (this) {
            Topics -> Tag.Topics
            ISH -> Tag.ISH
            Learn -> Tag.Learn
            ManageInterviews -> Tag.ManageInterviews
        }
    }

    fun getSectionItems(): List<Tag> {
        return when (this) {
            Topics -> listOf(Tag.Java,
                    Tag.Android,
                    Tag.Kotlin,
                    Tag.IOS,
                    Tag.Swift,
                    Tag.JavaScript,
                    Tag.SQL,
                    Tag.DataStructure,
                    Tag.DesignPatterns,
                    Tag.Algorithms,
                    Tag.GIT)
            ISH -> listOf(Tag.Mobile,
                    Tag.Android,
                    Tag.IOS,
                    Tag.Backend,
                    Tag.Modules,
                    Tag.FrontEnd,
                    Tag.Support)
            Learn -> listOf()
            ManageInterviews ->
                listOf(Tag.Interviewers,
                        Tag.Candidate,
                        Tag.Interviews)
        }
    }
}