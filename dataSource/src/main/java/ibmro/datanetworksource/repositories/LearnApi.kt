package ibmro.datanetworksource.repositories

import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import ibmro.datanetworksource.repositories.entities.databaseModels.responseModel.LearnItemResponse
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import java.util.concurrent.TimeUnit

interface LearnApi {

    @GET("760f8443dc294295b2bc08e2ee9c4bd0")
    fun getAllArticles(): Single<LearnItemResponse>
}

object LearnRetrofitClient {
    private var ourInstance: Retrofit? = null

    val instance: Retrofit
        get() {
            if (ourInstance == null) {
                val moshi = Moshi.Builder()
                        .add(KotlinJsonAdapterFactory())
                        .build()

                val okHttpClient = OkHttpClient.Builder()
                        .retryOnConnectionFailure(true)
                        .connectTimeout(60, TimeUnit.SECONDS)
                        .readTimeout(60, TimeUnit.SECONDS)
                        .writeTimeout(60, TimeUnit.SECONDS)
                        .build()

                ourInstance = Retrofit.Builder()
                        .baseUrl("https://newsapi.org/v2/top-headlines?sources=techcrunch&apiKey=")
                        .addConverterFactory(MoshiConverterFactory.create(moshi))
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                        .client(okHttpClient)
                        .build()
            }
            return ourInstance!!
        }

}