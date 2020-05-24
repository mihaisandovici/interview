package ibmro.ishqa.di.app

import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import ibmro.ishqa.App
@Component(modules = arrayOf(
        AppModule::class,
        ActivityModule::class,
        AndroidSupportInjectionModule::class))
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: App):Builder

        fun build(): AppComponent
    }

    fun inject(app: App)
}