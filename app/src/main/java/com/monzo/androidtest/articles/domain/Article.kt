package com.monzo.androidtest.articles.domain

import android.os.Parcel
import android.os.Parcelable
import java.util.*

data class Article(
        val id: String,
        val thumbnail: String,
        val sectionId: String,
        val sectionName: String,
        val published: Date,
        val title: String,
        val url: String,
        val body: String
) : Parcelable {
        // Implementing Parcelable interface methods
        override fun writeToParcel(parcel: Parcel, flags: Int) {
                parcel.writeString(id)
                parcel.writeString(thumbnail)
                parcel.writeString(sectionId)
                parcel.writeString(sectionName)
                parcel.writeSerializable(published)
                parcel.writeString(title)
                parcel.writeString(url)
                parcel.writeString(body)
        }

        override fun describeContents(): Int {
                return 0
        }

        companion object CREATOR : Parcelable.Creator<Article> {
                override fun createFromParcel(parcel: Parcel): Article {
                        return Article(
                                id = parcel.readString()!!,
                                thumbnail = parcel.readString()!!,
                                sectionId = parcel.readString()!!,
                                sectionName = parcel.readString()!!,
                                published = parcel.readSerializable() as Date,
                                title = parcel.readString()!!,
                                url = parcel.readString()!!,
                                body = parcel.readString()!!
                        )
                }

                override fun newArray(size: Int): Array<Article?> {
                        return arrayOfNulls(size)
                }
        }
}

