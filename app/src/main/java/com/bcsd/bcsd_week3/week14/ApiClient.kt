package com.bcsd.bcsd_week3.week14

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

object ApiClient {
    private const val BASE_URL = "https://api.github.com/"
    const val PER_PAGE = 30

    val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(120, TimeUnit.SECONDS)
        .readTimeout(120, TimeUnit.SECONDS)
        .writeTimeout(120, TimeUnit.SECONDS)
        .build()

    var gson = GsonBuilder().setLenient().create()

    val retrofit : Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
    }
    val apiService: ApiService by lazy { retrofit.create(ApiService::class.java) }
}

interface ApiService {
    @GET("search/repositories")
    fun searchRepositories(
        @Query("q") repoName: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = ApiClient.PER_PAGE,
    ): Call<GithubRepositoriesDTO>

    @GET("users/{login}/repos")
    fun getUserRepositories(
        @Path(value = "login") login: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = ApiClient.PER_PAGE,
    ): Call<List<GithubRepositoryDTO>>

}