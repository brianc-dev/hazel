package com.briancaldera.hazel.ui

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeActivityTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule(HomeActivity::class.java)

    @Test
    fun assertThat_SnackBarIsDisplayed() {
        composeTestRule.onNodeWithText("User is signed in").assertDoesNotExist()
        composeTestRule.onNodeWithTag("UserIcon", useUnmergedTree = true).performClick()
        composeTestRule.onNodeWithTag("OptionsMenu", useUnmergedTree = true).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag("SignOut", useUnmergedTree = true).assertExists().assertIsDisplayed().performClick()
        composeTestRule.onNodeWithTag("OptionsMenu", useUnmergedTree = true).assertDoesNotExist()
        composeTestRule.onNodeWithText("User is signed in").assertExists().assertIsDisplayed()
    }

    @Test
    fun  assertThat_userIconExists() {
        composeTestRule.onNodeWithTag("UserIcon").assertExists()
    }

    @Test
    fun assertThat_UserMenuIsDisplayed() {
        composeTestRule.onNodeWithTag("OptionsMenu", useUnmergedTree = true).assertDoesNotExist()
        composeTestRule.onNodeWithTag("UserIcon").performClick()
        composeTestRule.onNodeWithTag("OptionsMenu", useUnmergedTree = true).assertIsDisplayed()
    }
}