package ibmro.ishqa.onboarding

import ibmro.ishqa.R

enum class OnboardingItem(val iconResId: Int, val heading: Int, val description: Int) {
    ASK( R.drawable.ask_icon, R.string.ask, R.string.ask_desc),
    LEARN( R.drawable.learn_icon, R.string.learn, R.string.learn_desc),
    MANAGE_INTERVIEW( R.drawable.manage_interview_icon, R.string.manage_interview, R.string.manage_interview_desc),
    CONTRIBUTE(  R.drawable.contribute_icon, R.string.contribute, R.string.contribute_desc)
}