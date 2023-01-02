package com.krishnanand.clickrow.activity

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createEmptyComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.krishnanand.clickrow.InjectableScenarioRule
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain

class ClickRowActivityTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val emptyComposeRule = createEmptyComposeRule()

    val activityScenarioRule = InjectableScenarioRule(ClickRowActivity::class.java) {

    }

    @get:Rule
    val testRule = RuleChain.outerRule(instantExecutorRule).around(activityScenarioRule)

    @Test
    fun testLaunchActivity() {
        activityScenarioRule.launch() {
            with(emptyComposeRule) {
                onNodeWithText(MESSAGE1, substring = true, useUnmergedTree = true).assertIsDisplayed()
                onNodeWithText(MESSAGE2, substring = true, useUnmergedTree = true).assertIsDisplayed()
            }
        }
    }

    companion object {
        private const val MESSAGE1 = "ABCDE12345"
        private const val MESSAGE2 = "BCDEF23456"
    }
}