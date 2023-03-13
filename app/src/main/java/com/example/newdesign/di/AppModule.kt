package com.example.newdesign.di
import android.util.Log
import com.example.newdesign.BuildConfig
import com.example.newdesign.api.ApiService
import com.example.newdesign.fragment.loginandforgetpassword.LoginFragment
import com.example.newdesign.utils.Constans.BASE_URL
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.SocketTimeoutException
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun getGson(): Gson {
        return GsonBuilder().serializeNulls().setLenient().create()
    }

    @Provides
    @Singleton
    fun getInterceptor(): Interceptor {

        val token= LoginFragment.instance?.sp?.getUser()?.token
        return Interceptor {
            val request = it.request().newBuilder()
            val requests: Request = it.request()
            val response = it.proceed(requests)
            when (response.code) {
                400 -> {
                    //Show Bad Request Error Message
                    Log.e("tag","bad request")
                }
                401 -> {
                    //Show UnauthorizedError Message

                }
                403 -> {
                    //Show Forbidden Message
                }
                404 -> {
                    //Show NotFound Message
                    Log.e("tag","404")
                }
                // ... and so on
            }


            request.addHeader("Authorization", "Bearer "+token)
            val actualRequest = request.build()
            it.proceed(it.request())
        }
    }

    @Provides
    @Singleton
    fun getOkHttpClient(
        interceptor: Interceptor
    ): OkHttpClient {


        val logging=HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

//        val levelType: HttpLoggingInterceptor.Level = if (BuildConfig.DEBUG)
//            HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
//        val logging = HttpLoggingInterceptor().setLevel(levelType)
        val httpBuilder = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(50, TimeUnit.SECONDS)

        return httpBuilder
            .addInterceptor(logging)
            .build()


    }


    @Provides
    fun okHttpClient(): OkHttpClient {

        val levelType: HttpLoggingInterceptor.Level = if (BuildConfig.DEBUG)
            HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE

        val logging = HttpLoggingInterceptor()
        logging.setLevel(levelType)

        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }



    private val logging: HttpLoggingInterceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)


    private fun getOkHttpClient(): OkHttpClient {
        val token= LoginFragment.instance?.sp?.getUser()?.token
        val okHttpClientBuilder = OkHttpClient.Builder()

        if (BuildConfig.DEBUG) {

            okHttpClientBuilder
                .addInterceptor(logging)

        }

        okHttpClientBuilder.addInterceptor { chain ->

            try {
                val originalRequest = chain.request()
                val requestBuilder =
                    originalRequest.newBuilder()

                            requestBuilder.addHeader("Authorization", "Bearer $token")

                chain.proceed(requestBuilder.build())
            } catch (exception: SocketTimeoutException) {
                exception.printStackTrace()
                chain.proceed(chain.request())
            }

        }

        return okHttpClientBuilder.build()
    }


    @Provides
    @Singleton
    fun provideRetrofitInstance(
        // Potential dependencies of this type
    ): ApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(getOkHttpClient())
            .build()
            .create(ApiService::class.java)
    }

}