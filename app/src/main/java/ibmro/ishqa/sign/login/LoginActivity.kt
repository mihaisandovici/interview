package ibmro.ishqa.sign.login

import android.content.Intent
import android.os.Bundle
import ibmro.common.extension.clickOn
import ibmro.common.extension.toast
import ibmro.ishqa.MainActivity
import ibmro.ishqa.R
import ibmro.ishqa.base.BaseActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity<LoginContract.View, LoginContract.Presenter>(), LoginContract.View {

    lateinit override var presenter: LoginContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        presenter = LoginPresenter(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        skip_login.clickOn {
            startActivity(Intent(this, MainActivity::class.java))
        }

        login_btn.clickOn {
            login()
        }
    }

    private fun login() {
        presenter.login(username.text.toString(), password.text.toString())
    }

    override fun showToast(msg: String) = toast(msg)

}