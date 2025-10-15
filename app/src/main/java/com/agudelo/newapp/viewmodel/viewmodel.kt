package com.agudelo.newapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agudelo.newapp.provider.NewsApiProvider
import com.agudelo.newapp.model.News
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.text.isBlank
import com.agudelo.newapp.BuildConfig

class NewsViewModel : ViewModel() {

    private val _newsList = MutableStateFlow<List<News>>(emptyList())
    val newsList: StateFlow<List<News>> = _newsList

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    val apiKey = BuildConfig.NEWS_API_KEY

    init {
        fetchNews()
    }

    fun fetchNews(country: String = "us") {
        viewModelScope.launch {
            try {
                val response = NewsApiProvider.api.getTopHeadlines(country, apiKey)
                _newsList.value = response.articles
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun searchNews(query: String) {
        _searchQuery.value = query

        if (query.isBlank()) {
            fetchNews()
            return
        }

        viewModelScope.launch {
            try {
                val response = NewsApiProvider.api.searchNews(query, apiKey)
                _newsList.value = response.articles
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun clearSearch() {
        _searchQuery.value = ""
        fetchNews()
    }
}