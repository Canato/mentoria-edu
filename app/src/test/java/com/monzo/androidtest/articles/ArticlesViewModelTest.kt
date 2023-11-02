package com.monzo.androidtest.articles

import com.monzo.androidtest.util.RxSchedulerRule
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.monzo.androidtest.articles.data.ArticlesRepository
import com.monzo.androidtest.articles.presentation.ArticlesViewModel
import io.reactivex.Single
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

class ArticlesViewModelTest {
    @Rule @JvmField val rxSchedulerRule = RxSchedulerRule()
    @Rule @JvmField val rule = InstantTaskExecutorRule()

    private val repository = mock<ArticlesRepository>()

    private val viewModel get() = ArticlesViewModel(repository)

    @Test
    fun test() {
        whenever(repository.latestFintechArticles()).thenReturn(Single.just(emptyList()))

        Assert.assertNotNull(viewModel.state.value)
    }
}
