package ibmro.ishqa.splash

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import ibmro.ishqa.App
import ibmro.ishqa.MainActivity
import ibmro.ishqa.R
import ibmro.ishqa.onboarding.OnboardingActivity
import ibmro.ishqa.profile.ProfileActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            kotlin.run {
                startNewActivity()
            }
        }, TIME_OUT)
    }

    private fun startNewActivity() {

        val onboardingComplete= App.prefs.completedOnboarding
        val intent : Intent
        if(onboardingComplete)
        {
            if(App.prefs.completedProfileStep)
                intent = Intent(this, MainActivity::class.java)
            else
                intent = Intent(this, ProfileActivity::class.java)
        }
        else {
            App.prefs.completedOnboarding=true
            intent = Intent(this, OnboardingActivity::class.java)
        }
        startActivity(intent)
        finish()
    }
}
