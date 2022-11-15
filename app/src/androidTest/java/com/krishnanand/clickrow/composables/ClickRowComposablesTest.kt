package com.krishnanand.clickrow.composables

import android.content.Context
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ApplicationProvider
import com.krishnanand.clickrow.R
import com.krishnanand.clickrow.data.ClickRow
import com.krishnanand.clickrow.ui.theme.ClickRowTheme
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ClickRowComposablesTest {

    @get:Rule
    val composeRule = createComposeRule()

    private lateinit var context: Context

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
    }

    @Test
    fun testClickUi() {
        var wasClicked = false
        val onClickAction = { string: String ->
            wasClicked = true
            MatcherAssert.assertThat(string, Matchers.`is`(context.getString(R.string.redeem_coupon)))
        }
        val clickRow = ClickRow(
            MESSAGE,
            EXPIRATION
        )
        composeRule.setContent {
            ClickRowTheme {
                ClickRowUi(clickRow = clickRow, onClick = onClickAction, Modifier)
            }
        }
        with(composeRule) {
            onNodeWithText(MESSAGE).assertExists()
            onNodeWithText(EXPIRATION).assertExists().performClick()
        }
        MatcherAssert.assertThat(wasClicked, Matchers.`is`(true))
    }

    companion object {
        private const val MESSAGE = "message"
        private const val EXPIRATION = "expiration"
    }
}