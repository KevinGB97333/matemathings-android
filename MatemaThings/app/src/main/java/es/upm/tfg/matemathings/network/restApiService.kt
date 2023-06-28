package es.upm.tfg.matemathings.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*
import es.upm.tfg.matemathings.network.models.*
import es.upm.tfg.matemathings.models.concept.ConceptsResponse

private const val BASE_URL = "https://kevin97333.pythonanywhere.com/"

var mHttpLoggingInterceptor = HttpLoggingInterceptor()
	.setLevel(HttpLoggingInterceptor.Level.BODY)

var mOkHttpClient = OkHttpClient
	.Builder()
	.addInterceptor(mHttpLoggingInterceptor)
	.build()

private val moshi = Moshi.Builder()
	.add(KotlinJsonAdapterFactory())
	.build()


private val retrofit = Retrofit.Builder()
	.addConverterFactory(MoshiConverterFactory.create(moshi))
	.baseUrl(BASE_URL)
	.client(mOkHttpClient)
	.build()

interface restApiService {
	//GET

	@GET("videos")
	suspend fun getVideos(@Query("concept_id") conceptId: Int) : Response<VideosResponse>

	@GET("concepts")
	suspend fun getConcepts() : Response<ConceptsResponse>
}

object restApi {
	val retrofitService : restApiService by lazy { retrofit.create(restApiService::class.java) }
}