package ibmro.ishqa.onboarding
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import ibmro.ishqa.R


class OnboardingActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)
        addFragmentToActivity()
    }

    private fun addFragmentToActivity() {
        supportFragmentManager.beginTransaction().add(R.id.onboarding,OnboardingFragment()).commit()
    }

}
