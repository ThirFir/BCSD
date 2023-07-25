package com.bcsd.bcsd_week3.week14

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.net.toUri
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bcsd.bcsd_week3.R
import com.bcsd.bcsd_week3.databinding.ActivityUserRepositoriesBinding
import com.bcsd.bcsd_week3.week14.model.GithubRepository

class UserRepositoriesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserRepositoriesBinding
    private lateinit var owner: GithubRepository.Owner
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserRepositoriesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        owner = if(Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
           intent.getParcelableExtra("owner")!!
        } else intent.getParcelableExtra("owner", GithubRepository.Owner::class.java)!!

        binding.recyclerViewUserRepositories.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewUserRepositories.adapter = UserRepositoryAdapter {
            Log.d("Activity2", "onClick: $it")
            val browserIntent = Intent(Intent.ACTION_VIEW, it.htmlUrl.toUri())
            startActivity(browserIntent)
        }
        ApiClient.apiService.getUserRepositories(owner.name!!, 1).enqueue(object : retrofit2.Callback<List<GithubRepositoryDTO>> {
            override fun onResponse(
                call: retrofit2.Call<List<GithubRepositoryDTO>>,
                response: retrofit2.Response<List<GithubRepositoryDTO>>
            ) {
                if (response.isSuccessful) {
                    val githubRepo = response.body()
                    val githubReposList = mutableListOf<GithubRepository>().also {
                        it.addAll(githubRepo!!.map { repoDTO -> repoDTO.toGithubRepository() })
                    }
                    (binding.recyclerViewUserRepositories.adapter as UserRepositoryAdapter).submitList(githubReposList)
                    binding.progressBarUserRepositories.visibility = View.GONE
                }
            }

            override fun onFailure(call: retrofit2.Call<List<GithubRepositoryDTO>>, t: Throwable) {
                Log.d("Activity2", "onFailure: ${t.message}")
            }
        })

        binding.recyclerViewUserRepositories.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if(!binding.recyclerViewUserRepositories.canScrollVertically(1)) {
                    ApiClient.apiService.getUserRepositories(owner.name!!,
                        (binding.recyclerViewUserRepositories.adapter as UserRepositoryAdapter).itemCount / ApiClient.PER_PAGE + 1)
                        .enqueue(object : retrofit2.Callback<List<GithubRepositoryDTO>> {
                        override fun onResponse(
                            call: retrofit2.Call<List<GithubRepositoryDTO>>,
                            response: retrofit2.Response<List<GithubRepositoryDTO>>
                        ) {
                            if (response.isSuccessful) {
                                val githubRepo = response.body()
                                val githubReposList = mutableListOf<GithubRepository>().also {
                                    it.addAll((binding.recyclerViewUserRepositories.adapter as UserRepositoryAdapter).currentList)
                                    it.addAll(githubRepo!!.map { repoDTO -> repoDTO.toGithubRepository() })
                                }
                                (binding.recyclerViewUserRepositories.adapter as UserRepositoryAdapter).submitList(
                                    githubReposList
                                )
                            }
                        }

                        override fun onFailure(call: retrofit2.Call<List<GithubRepositoryDTO>>, t: Throwable) {
                            Log.d("Activity2", "onFailure: ${t.message}")
                        }
                    })
                }
            }
        })
    }
}