package ibmro.ishqa.di.app

import android.content.Context
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import dagger.Binds
import dagger.Module
import dagger.Provides
import ibmro.common.androidCommon.SharedPref
import ibmro.datanetworksource.repositories.ISHApi
import ibmro.ishqa.App
import io.reactivex.schedulers.Schedulers
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

@Module
abstract class AppModule {
    @Module
    companion object {
        @Provides
        @JvmStatic
        fun provideMoshi(): Moshi =
                Moshi.Builder()
                        .add(KotlinJsonAdapterFactory())
                        .build()

        @Provides
        @JvmStatic
        fun provideRetrofit(moshi: Moshi, okHttpClient: OkHttpClient): Retrofit =
                Retrofit.Builder()
                        .baseUrl("http://kcm6lp25.herrenberg.de.ibm.com:9080/InterviewAppREST/backend/")
                        .addConverterFactory(MoshiConverterFactory.create(moshi))
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                        .client(okHttpClient)
                        .build()

        @Provides
        @JvmStatic
        fun provideOkHttp(appContext: Context): OkHttpClient =
                OkHttpClient.Builder()
                        .retryOnConnectionFailure(true)
                        .cache(Cache(appContext.cacheDir, 1024 * 1024 * 16))
                        .connectTimeout(60, TimeUnit.SECONDS)
                        .readTimeout(60, TimeUnit.SECONDS)
                        .writeTimeout(60, TimeUnit.SECONDS)
                        .build()

        @Provides
        @JvmStatic
        fun provideGitHubApi(retrofit: Retrofit): ISHApi =
                retrofit.create(ISHApi::class.java)

        @Provides
        @JvmStatic
        fun provideSharedPrefs(context: Context): SharedPref =
               App.prefs
//        @Provides
//        @JvmStatic
//        fun provideSharedPrefs(context: Context): SharedPref =
//                SharedPref(context.getSharedPreferences("ibmro.sharedpref", Context.MODE_PRIVATE), false)
    }

    @Binds
    abstract fun bindApplicationContext(app: App): Context
}