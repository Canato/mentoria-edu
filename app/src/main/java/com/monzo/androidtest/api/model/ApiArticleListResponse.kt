package com.monzo.androidtest.api.model

data class ApiArticleListResponse(val response: ApiArticleList)

data class ApiArticleList(val results: List<ApiArticle>)

data class ApiArticleDetailsResponse (val response: ApiArticleContent)

data class ApiArticleContent (val content: ApiArticle)

