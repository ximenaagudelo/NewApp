package com.agudelo.newapp.model

data class NewsApiResponse(
    val status: String,
    val code: String?,
    val articles: List<News>
)
