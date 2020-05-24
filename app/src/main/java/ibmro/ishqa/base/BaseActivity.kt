package ibmro.ishqa.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import ibmro.ishqa.App
import java.util.*

abstract class BaseActivity<V : BaseView, T : BasePresenter<V>>
    : AppCompatActivity(), BaseView {

    protected abstract var presenter: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("activity presenter", "attach")

        setLanguage()
    }

    fun setLanguage() {
        val language = App.prefs.language

        val locale = Locale(language)
        Locale.setDefault(locale)
        val config = resources.configuration
        config.setLocale(locale)
        createConfigurationContext(config)
        resources.updateConfiguration(config, resources.displayMetrics)

    }

    override fun onStart() {
        super.onStart()
        presenter.attachView(this as V)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }


}