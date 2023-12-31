package com.monzo.androidtest.api.model

import java.util.Date

data class ApiArticle(
    val id: String,
    val sectionId: String,
    val sectionName: String,
    val webPublicationDate: Date,
    val webTitle: String,
    val webUrl: String,
    val apiUrl: String,
    val fields: ApiArticleFields?
)

data class ApiArticleFields(
    val headline: String?,
    val main: String?,
    val body: String?,
    val thumbnail: String?
)
