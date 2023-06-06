package com.cailloutr.rightnewscompose.ui.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.printToLog
import org.junit.Rule
import org.junit.Test

class AppBarTests {

    @get:Rule
    val composeTestRule = createComposeRule()

    @OptIn(ExperimentalMaterial3Api::class)
    @Test
    fun smallAppBarTest() {
        composeTestRule.setContent {
            SmallAppBar(
                title = "Main Screen",
                navigationIcon = { /*TODO*/ },
                scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
            )
        }

        composeTestRule.onRoot(useUnmergedTree = true).printToLog("currentLabelExists")

        composeTestRule
            .onNodeWithText("Main Screen")
            .assertIsDisplayed()
    }
}