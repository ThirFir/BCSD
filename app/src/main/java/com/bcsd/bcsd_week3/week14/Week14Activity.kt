package com.bcsd.bcsd_week3.week14

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Xml.Encoding
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

        binding.recyclerViewSearchUser.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewSearchUser.adapter = FoundUserAdapter(mutableListOf())

        val retrofitCallback = object : retrofit2.Callback<GithubUserDTO> {
            override fun onResponse(
                call: retrofit2.Call<GithubUserDTO>,
                response: retrofit2.Response<GithubUserDTO>
            ) {
                if (response.isSuccessful) {
                    val githubUser = response.body()
                    Log.d("MainActivity", response.toString())
                    Log.d("MainActivity", "githubUser: $githubUser")
                    githubUser?.let { user ->
                        (binding.recyclerViewSearchUser.adapter as FoundUserAdapter).updateList(
                            user.users
                        )
                    }
                }
            }

            override fun onFailure(call: retrofit2.Call<GithubUserDTO>, t: Throwable) {
                Log.d("MainActivity", "onFailure: ${t.message}")
            }
        }

        binding.textInputSearchUser.addTextChangedListener {
            ApiClient.apiService.searchUsers(it.toString(),
                (binding.recyclerViewSearchUser.adapter as FoundUserAdapter).itemCount / ApiClient.PER_PAGE + 1)
                .enqueue(retrofitCallback)
        }

        binding.recyclerViewSearchUser.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (!binding.recyclerViewSearchUser.canScrollVertically(1)) {
                    ApiClient.apiService.searchUsers(binding.textInputSearchUser.text.toString(),
                        (binding.recyclerViewSearchUser.adapter as FoundUserAdapter).itemCount / ApiClient.PER_PAGE + 1)
                        .enqueue(retrofitCallback)
                }
            }
        })
    }
}