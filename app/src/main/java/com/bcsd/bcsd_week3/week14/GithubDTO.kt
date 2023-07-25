package com.bcsd.bcsd_week3.week14

import com.google.gson.annotations.SerializedName

/*data class GithubRepository(
    @SerializedName("name") val name: String,
    @SerializedName("html_url") val htmlUrl: String,
    @SerializedName("login") val login: String,
)

data class GithubRepositoryDTO(
    @SerializedName("total_count") val totalCount: Int,
    @SerializedName("items") val users: List<GithubRepository>
)*/

data class GithubRepositoriesDTO(
    @SerializedName("total_count") val totalCount: Int,
    @SerializedName("items") val repositories: List<GithubRepositoryDTO>,
)
data class GithubRepositoryDTO(
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("stargazers_count") val stargazersCount: Int,
    @SerializedName("forks_count") val forksCount: Int,
    @SerializedName("html_url") val htmlUrl: String,
    @SerializedName("url") val apiUrl: String,
    @SerializedName("owner") val owner: OwnerDTO,

) {
    data class OwnerDTO(
        @SerializedName("login") val login: String,
        @SerializedName("repos_url") val reposUrl: String,
    )
}