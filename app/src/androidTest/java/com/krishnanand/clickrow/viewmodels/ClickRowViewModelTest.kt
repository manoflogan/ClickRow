package com.krishnanand.clickrow.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import com.krishnanand.clickrow.ClickRowApplication
import com.krishnanand.clickrow.dagger.DaggerClickRowComponent
import com.krishnanand.clickrow.data.ClickRow
import com.krishnanand.clickrow.repository.ClickRowRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ClickRowViewModelTest {

    private lateinit var clickRowViewModel: ClickRowViewModel

    private lateinit var clickRowRepository: ClickRowRepository

    private lateinit var clickRowApplication: ClickRowApplication

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        clickRowApplication = ApplicationProvider.getApplicationContext()
        val component = DaggerClickRowComponent.factory().create(clickRowApplication)
        clickRowRepository = component.clickRowRepository
        clickRowViewModel = ClickRowViewModel(clickRowRepository)
    }


    @Test
    fun validateThatDataIsLoadedCorrectly() = runTest {
        val expectedValue = clickRowRepository.fetchClickRows()
        val actualValue = mutableListOf<List<ClickRow>>()
        val job = launch(UnconfinedTestDispatcher()) {
            clickRowViewModel.fetchAndInitialiseClickRows()
            clickRowViewModel.clickRowsFlow.toList(actualValue)
        }
        MatcherAssert.assertThat(expectedValue, CoreMatchers.`is`(actualValue.last()))
        job.cancel()
    }
}