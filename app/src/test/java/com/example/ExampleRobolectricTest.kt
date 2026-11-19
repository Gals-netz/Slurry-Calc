package com.example

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performTextReplacement
import androidx.test.core.app.ApplicationProvider
import com.example.ui.SlurryCalculatorScreen
import com.example.ui.SlurryViewModel
import com.example.ui.theme.CekSedimenTheme
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `read string from context`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("Cek Sedimen", appName)
    }

    @Test
    fun `calculate button updates Cv and Cw outputs`() {
        val viewModel = SlurryViewModel()
        composeTestRule.setContent {
            CekSedimenTheme {
                SlurryCalculatorScreen(viewModel = viewModel)
            }
        }

        // Initial placeholder is shown before pressing HITUNG
        composeTestRule.onNodeWithText("Siap Menghitung Komposisi Slurry")
            .performScrollTo()
            .assertIsDisplayed()

        // Select preset 2.20 and input SG 1.24
        composeTestRule.onNodeWithTag("preset_2_20")
            .performScrollTo()
            .performClick()
        composeTestRule.onNodeWithTag("sg_input")
            .performScrollTo()
            .performTextReplacement("1.24")

        // Click docked HITUNG button (always visible without scrolling)
        composeTestRule.onNodeWithTag("calculate_button")
            .assertIsDisplayed()
            .performClick()

        // Verify exact Cv and Cw outputs
        composeTestRule.onNodeWithTag("out_cv_persen")
            .performScrollTo()
            .assertIsDisplayed()
            .assertTextEquals("20,00%")
        composeTestRule.onNodeWithTag("out_cw_persen")
            .performScrollTo()
            .assertIsDisplayed()
            .assertTextEquals("35,48%")
    }
}
