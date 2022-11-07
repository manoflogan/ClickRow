package com.krishnanand.clickrow.repository

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.squareup.moshi.Moshi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
class ClickRowRepositoryTest {

    private lateinit var clickRowRepository: ClickRowRepositoryImpl
    private lateinit var testCoroutinesDispatchers: TestCoroutinesDispatchers
    private lateinit var context: Context

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        testCoroutinesDispatchers = TestCoroutinesDispatchers()
        clickRowRepository = ClickRowRepositoryImpl(
            Moshi.Builder(),
            testCoroutinesDispatchers,
            context
        )
    }

    @Test
    fun `validate that the assets are loaded correctly`() = runTest {
        clickRowRepository.fetchClickRows()
    }
}