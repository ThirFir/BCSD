package com.bcsd.bcsd_week3.week14

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bcsd.bcsd_week3.databinding.ActivityWeek14Binding

class Week14Activity : AppCompatActivity() {
    private lateinit var binding: ActivityWeek14Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeek14Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val linearLayoutManager = LinearLayoutManager(this)
        binding.recyclerViewSearchUser.layoutManager = linearLayoutManager
        binding.recyclerViewSearchUser.adapter = FoundRepositoryAdapter {
            Log.d("MainActivity", "onClick: $it")
            val intent = Intent(this, UserRepositoriesActivity::class.java).apply {
                putExtra("owner", it)
            }
            startActivity(intent)
        }


        binding.textInputSearchUser.addTextChangedListener {
            ApiClient.apiService.searchRepositories(it.toString(),
                (binding.recyclerViewSearchUser.adapter as FoundRepositoryAdapter).itemCount / ApiClient.PER_PAGE + 1)
                .enqueue(object : retrofit2.Callback<GithubRepositoriesDTO> {
                    override fun onResponse(
                        call: retrofit2.Call<GithubRepositoriesDTO>,
                        response: retrofit2.Response<GithubRepositoriesDTO>
                    ) {
                        if (response.isSuccessful) {
                            val githubReposDTO = response.body()
                            val githubRepos = githubReposDTO?.toGithubRepositories()
                            (binding.recyclerViewSearchUser.adapter as FoundRepositoryAdapter).submitList(
                                githubRepos?.repositories
                            )
                        }
                    }

                    override fun onFailure(call: retrofit2.Call<GithubRepositoriesDTO>, t: Throwable) {
                        Log.d("MainActivity", "onFailure: ${t.message}")
                    }
                })
        }

        binding.recyclerViewSearchUser.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if(!binding.recyclerViewSearchUser.canScrollVertically(1)) {
                    ApiClient.apiService.searchRepositories(binding.textInputSearchUser.text.toString(),
                        (binding.recyclerViewSearchUser.adapter as FoundRepositoryAdapter).itemCount / ApiClient.PER_PAGE + 1)
                        .enqueue(object : retrofit2.Callback<GithubRepositoriesDTO> {
                            override fun onResponse(
                                call: retrofit2.Call<GithubRepositoriesDTO>,
                                response: retrofit2.Response<GithubRepositoriesDTO>
                            ) {
                                if (response.isSuccessful) {
                                    val githubReposDTO = response.body()
                                    val githubRepos = githubReposDTO?.toGithubRepositories()

                                    (binding.recyclerViewSearchUser.adapter as FoundRepositoryAdapter).submitList(
                                        (binding.recyclerViewSearchUser.adapter as FoundRepositoryAdapter).currentList + githubRepos?.repositories as MutableList
                                    )
                                }
                            }

                            override fun onFailure(call: retrofit2.Call<GithubRepositoriesDTO>, t: Throwable) {
                                Log.d("MainActivity", "onFailure: ${t.message}")
                            }
                        })

                }
            }
        })
    }
}