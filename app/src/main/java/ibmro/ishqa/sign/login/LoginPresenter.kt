package ibmro.ishqa.sign.login

import android.content.Context
import android.content.Intent
import ibmro.common.extension.applySchedulers
import ibmro.datanetworksource.interactors.sign.LoginInteractor
import ibmro.ishqa.App
import ibmro.ishqa.MainActivity
import ibmro.ishqa.base.BasePresenterImpl
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy

class LoginPresenter(
        private var context: Context
) : BasePresenterImpl<LoginContract.View>(), LoginContract.Presenter {

    private var disposable = CompositeDisposable()

    override fun login(username: String, password: String) {
        disposable.add(LoginInteractor(App.ishApi)
                .invoke(username, password)
                .applySchedulers()
                .subscribeBy(
                        onSuccess = {
                            App.prefs.role = it.role
                            context.startActivity(Intent(context, MainActivity::class.java))

                        },
                        onError = {

                        }
                ))
    }

}