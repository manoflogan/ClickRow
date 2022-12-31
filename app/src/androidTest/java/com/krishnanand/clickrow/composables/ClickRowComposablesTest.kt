package com.krishnanand.clickrow.composables

import android.content.Context
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.core.app.ApplicationProvider
import com.krishnanand.clickrow.R
import com.krishnanand.clickrow.data.ClickRow
import com.krishnanand.clickrow.ui.theme.ClickRowTheme
import com.krishnanand.clickrow.viewmodels.ClickRowViewModel
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import io.mockk.verify
import kotlinx.coroutines.flow.MutableStateFlow
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ClickRowComposablesTest {

    @get:Rule
    val composeRule = createComposeRule()

    @get:Rule
    val mockkRule = MockKRule(this)

    private lateinit var context: Context

    @MockK
    private lateinit var viewModel: ClickRowViewModel

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
    }

    @Test
    fun testClickRowUi() {
        var wasClicked = false
        val onClickAction = { string: String ->
            wasClicked = true
            MatcherAssert.assertThat(string, Matchers.`is`(context.getString(R.string.redeem_coupon)))
        }
        composeRule.setContent {
            ClickRowTheme {
                ClickRowUi(clickRow = CLICK_ROW, onClick = onClickAction, Modifier)
            }
        }
        with(composeRule) {
            onNodeWithText(MESSAGE).assertExists()
            onNodeWithText(EXPIRATION).assertExists().performClick()
        }
        MatcherAssert.assertThat(wasClicked, Matchers.`is`(true))
    }

    @Test
    fun testClickRowListUi() {
        var wasClicked = false
        val onClickAction = { string: String ->
            wasClicked = true
            MatcherAssert.assertThat(string, Matchers.`is`(context.getString(R.string.redeem_coupon)))
        }
        every {
            viewModel.clickRowsFlow
        } returns MutableStateFlow(listOf(CLICK_ROW))
        composeRule.setContent {
            ClickRowTheme {
                ClickRowListUi(viewModel, onClick = onClickAction, Modifier)
            }
        }
        with(composeRule) {
            onNodeWithText(MESSAGE).assertExists()
            onNodeWithText(EXPIRATION).assertExists().performClick()
        }
        MatcherAssert.assertThat(wasClicked, Matchers.`is`(true))
        verify {
            viewModel.clickRowsFlow
        }
    }

    @Test
    fun testClickRowComposables() {
        every {
            viewModel.clickRowsFlow
        } returns MutableStateFlow(listOf(CLICK_ROW))
        composeRule.setContent {
            ClickRowTheme {
                ClickRowComposables(viewModel, Modifier)
            }
        }
        with(composeRule) {
            onNodeWithText(MESSAGE).assertExists()
            onNodeWithText(EXPIRATION).assertExists().performClick()
            onNodeWithTag(hasText(context.getString(R.string.redeem_coupon_accessibility))).assertIsDisplayed()
        }
        verify {
            viewModel.clickRowsFlow
        }
    }

    companion object {
        private const val MESSAGE = "message"
        private const val EXPIRATION = "expiration"
        val CLICK_ROW = ClickRow(
            MESSAGE,
            EXPIRATION
        )
    }
}