package ibmro.ishqa.sign.login

import ibmro.ishqa.base.BasePresenter
import ibmro.ishqa.base.BaseView

object LoginContract {

    interface View : BaseView {

        fun showToast(msg: String)
    }

    interface Presenter : BasePresenter<View> {

        fun login(username: String, password: String)


    }

}