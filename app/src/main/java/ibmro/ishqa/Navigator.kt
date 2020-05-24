package ibmro.ishqa

import android.content.Context
import android.content.Intent
import ibmro.ishqa.onboarding.OnboardingActivity
import ibmro.ishqa.sectionItemsList.SectionItemListActivity
import javax.inject.Inject

class Navigator @Inject constructor(
        private val activityContext : Context
){
    fun toOnboardingActivity(){
        val intent = Intent(activityContext, OnboardingActivity::class.java)
        activityContext.startActivity(intent)
    }

    fun toItemListActivity() {
        val intent = Intent(activityContext, SectionItemListActivity::class.java)
        activityContext.startActivity(intent)
    }
}

