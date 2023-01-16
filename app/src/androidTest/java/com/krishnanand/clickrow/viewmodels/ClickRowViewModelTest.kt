package com.krishnanand.clickrow.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import com.krishnanand.clickrow.ClickRowApplication
import com.krishnanand.clickrow.ClickRowTestApplication
import com.krishnanand.clickrow.dagger.DaggerClickRowComponent
import com.krishnanand.clickrow.data.ClickRow
import com.krishnanand.clickrow.repository.ClickRowRepository
import io.mockk.InternalPlatformDsl.toArray
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

    private lateinit var clickRowApplication: ClickRowTestApplication

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
        launch(UnconfinedTestDispatcher()) {
            clickRowViewModel.fetchAndInitialiseClickRows()
            @Suppress("unchecked")
            clickRowViewModel.clickRowsFlow.collect {
                MatcherAssert.assertThat(expectedValue, CoreMatchers.`is`(it))
            }
        }.also {
            it.cancel()
        }

    }
}