package com.bcsd.bcsd_week3.week14

import android.util.Log
import com.bcsd.bcsd_week3.week14.model.GithubRepositories
import com.bcsd.bcsd_week3.week14.model.GithubRepository

fun GithubRepositoriesDTO.toGithubRepositories() : GithubRepositories =
    GithubRepositories(
        totalCount = totalCount,
        repositories = repositories.map { it.toGithubRepository() }
    )

fun GithubRepositoryDTO.toGithubRepository() : GithubRepository =
    GithubRepository(
        name = name,
        description = description,
        stargazersCount = stargazersCount,
        forksCount = forksCount,
        htmlUrl = htmlUrl,
        apiUrl = apiUrl,
        owner = owner.toOwner()
    )

fun GithubRepositoryDTO.OwnerDTO.toOwner() : GithubRepository.Owner =
    GithubRepository.Owner(
        name = login,
        reposUrl = reposUrl
    )