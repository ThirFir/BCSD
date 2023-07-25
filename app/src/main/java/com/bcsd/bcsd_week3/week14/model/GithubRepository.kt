package com.bcsd.bcsd_week3.week14.model

import android.os.Parcel
import android.os.Parcelable


data class GithubRepository(
    val name: String,
    val description: String? = "",
    val stargazersCount: Int,
    val forksCount: Int,
    val htmlUrl: String,
    val apiUrl: String,
    val owner: Owner
) {
    data class Owner (
        val name: String?,
        val reposUrl: String?,
    ) : Parcelable {
        constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString()
        )

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeString(name)
            parcel.writeString(reposUrl)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<Owner> {
            override fun createFromParcel(parcel: Parcel): Owner {
                return Owner(parcel)
            }

            override fun newArray(size: Int): Array<Owner?> {
                return arrayOfNulls(size)
            }
        }

    }

}