package ibmro.datanetworksource.repositories.entities

enum class Tag(value: Int) {
    Topics(1),
    ISH(2),
    Learn(3),
    ManageInterviews(4),
    Mobile(5),
    Android(6),
    IOS(7),
    Backend(8),
    Modules(9),
    FrontEnd(10),
    Support(11),
    Java(12),
    Kotlin(13),
    Swift(14),
    JavaScript(15),
    SQL(16),
    DataStructure(17),
    DesignPatterns(18),
    Algorithms(19),
    GIT(20),
    Candidate(21),
    Interviews(22),
    Interviewers(23);

    companion object {
        fun getItemByString(itemKey: String): Tag? {
            when (itemKey) {
                Topics.toString() -> return Topics
                ISH.toString() -> return ISH
                Learn.toString() -> return Learn
                Mobile.toString() -> return Mobile
                ManageInterviews.toString() -> return ManageInterviews
                Android.toString() -> return Android
                IOS.toString() -> return IOS
                Backend.toString() -> return Backend
                Modules.toString() -> return Modules
                FrontEnd.toString() -> return FrontEnd
                Support.toString() -> return Support
                Java.toString() -> return Java
                Kotlin.toString() -> return Kotlin
                Swift.toString() -> return Swift
                JavaScript.toString() -> return JavaScript
                SQL.toString() -> return SQL
                DataStructure.toString() -> return DataStructure
                DesignPatterns.toString() -> return DesignPatterns
                Algorithms.toString() -> return Algorithms
                GIT.toString() -> return GIT
                Candidate.toString() -> return Candidate
                Interviewers.toString() -> return Interviewers
                Interviews.toString() -> return Interviews

                else -> return null
            }
        }
    }


    override fun toString(): String {
        when (this) {
            Topics -> return "topics"
            ISH -> return "ish"
            Learn -> return "learn"
            Mobile -> return "mobile"
            ManageInterviews -> return "manageInterviews"
            Android -> return "android"
            IOS -> return "ios"
            Backend -> return "backend"
            Modules -> return "modules"
            FrontEnd -> return "frontend"
            Support -> return "support"
            Java -> return "java"
            Kotlin -> return "kotlin"
            Swift -> return "swift"
            JavaScript -> return "js"
            SQL -> return "sql"
            DataStructure -> return "dataStructure"
            DesignPatterns -> return "designPatterns"
            Algorithms -> return "algorithms"
            GIT -> return "git"
            Candidate -> return "candidate"
            Interviewers -> return "interviewers"
            Interviews -> return "interviews"
        }
    }
}
