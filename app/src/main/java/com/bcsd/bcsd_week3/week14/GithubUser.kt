package com.bcsd.bcsd_week3.week14

import com.google.gson.annotations.SerializedName

data class GithubUser(
    @SerializedName("login") val login: String,
    @SerializedName("html_url") val htmlUrl: String,
)

data class GithubUserDTO(
    @SerializedName("total_count") val totalCount: Int,
    @SerializedName("items") val users: List<GithubUser>
)
