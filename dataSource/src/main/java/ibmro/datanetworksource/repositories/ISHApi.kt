package ibmro.datanetworksource.repositories

import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import ibmro.datanetworksource.repositories.entities.databaseModels.requestModel.*
import ibmro.datanetworksource.repositories.entities.databaseModels.responseModel.*
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit

interface ISHApi {
    @POST("candidateManagement/getCandidates")
    fun getAllCandidates(@Body body: GetCandidatesRequest): Single<GetCandidateResponse>

    @POST("candidateManagement/addCandidate")
    fun addCandidate(@Body body: AddCandidateRequest): Single<BasicResponse>

    @POST("candidateManagement/updateCandidate")
    fun updateCandidate(@Body body: UpdateCandidateRequest): Single<BasicResponse>

    @HTTP(method = "DELETE", path = "candidateManagement/deleteCandidate", hasBody = true)
    fun deleteCandidate(@Body body: DeleteCandidateRequest): Single<BasicResponse>

    @POST("questionManagement/addQuestion")
    fun addQuestion(@Body body: QuestionRequest): Single<BasicResponse>

    @POST("questionManagement/getQuestionsByTags")
    fun getQuestionsBYTag(@Body body: GetQuestionsByTagRequest): Single<GetQuestionsByTagResponse>

    @HTTP(method = "DELETE", path = "questionManagement/deleteQuestion", hasBody = true)
    fun deleteQuestion(@Body body: DeleteRequest): Single<BasicResponse>

    @POST("questionManagement/updateQuestion")
    fun updateQuestion(@Body body: UpdateQuestionRequest): Single<BasicResponse>

    @POST("questionManagement/getQuestionsByIds")
    fun getQuestionsById(@Body body: GetQuestionByIdRequest): Single<List<QuestionResponse>>

    @POST("interviewManagement/addInterview")
    fun addInterview(@Body body: AddInterviewRequest): Single<AddInterviewResponse>

    @POST("interviewManagement/getInterviews")
    fun getInterviews(@Body body: GetInterviewsRequest): Single<GetInteviewResponse>

    @GET("candidateManagement/getCandidateById/{candidateId}/{role}")
    fun getCandidateById(@Path("candidateId") candidateId: Int, @Path("role") role: String): Single<GetCandidateByIdResponse>

    @HTTP(method = "DELETE", path = "interviewManagement/deleteInterview", hasBody = true)
    fun deleteInterview(@Body body: DeleteInterviewRequest): Single<BasicResponse>

    @POST("interviewerManagement/getInterviewersByIds")
    fun getInterviewersById(@Body body: GetInterviewersByIdRequest): Single<List<InterviewerResponse>>

    @GET("interviewerManagement/getInterviewers/ADMINISTRATOR")
    fun getAllInterviewers(): Single<GetInterviewersByIdResponse>

    @POST("interviewManagement/updateInterview")
    fun updateInterview(@Body body: EditInterviewRequest): Single<BasicResponse>

    @GET("sendInterviewReport/{interviewId}/{role}")
    fun senEmailReport(@Path("interviewId") interviewId: Int, @Path("role") role: String): Single<SendEmailResponse>

    @POST("interviewerManagement/addInterviewer")
    fun addInterviewer(@Body body: AddInterviewerRequest): Single<BasicResponse>

    @HTTP(method = "DELETE", path = "interviewerManagement/deleteInterviewer", hasBody = true)
    fun deleteInterviewer(@Body body: DeleteRequest): Single<BasicResponse>

    @POST("interviewerManagement/updateInterviewer")
    fun editInterviewer(@Body body: EditInterviewerRequest): Single<BasicResponse>

    @GET("userManagement/login/{username}/{password}")
    fun login(@Path("username") username: String,
              @Path("password") password: String): Single<LoginResponse>

    @POST("userManagement/signUp")
    fun signUp(@Body body: SignUpRequest): Single<BasicResponse>
}

object RetrofitClient {
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
                        .baseUrl("https://ituprocess.bluepostbox.de/InterviewAppREST/backend/")
                        .addConverterFactory(MoshiConverterFactory.create(moshi))
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                        .client(okHttpClient)
                        .build()
            }
            return ourInstance!!
        }

}