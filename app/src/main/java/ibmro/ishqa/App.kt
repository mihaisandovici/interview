package ibmro.ishqa

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import ibmro.common.androidCommon.SharedPref
import ibmro.datanetworksource.repositories.ISHApi
import ibmro.datanetworksource.repositories.RetrofitClient
import ibmro.ishqa.di.app.DaggerAppComponent
import javax.inject.Inject

class App : Application(), HasActivityInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

//    lateinit var component: AppComponent

    override fun onCreate() {
        super.onCreate()
        val retrofit = RetrofitClient.instance
        ishApi = retrofit.create(ISHApi::class.java)
        instace = this
        prefs = SharedPref(applicationContext)
        setup()
    }

    private fun setup() {
        DaggerAppComponent
                .builder()
                .application(this)
                .build()
                .inject(this)
    }

    override fun activityInjector(): AndroidInjector<Activity> =
        dispatchingAndroidInjector


    companion object {
        lateinit var ishApi: ISHApi
        lateinit var instace: App private set
        @SuppressLint("StaticFieldLeak")
        lateinit var prefs : SharedPref
    }
}