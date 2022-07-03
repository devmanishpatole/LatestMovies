package com.devmanishpatole.latestmovies.ui.screen

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.devmanishpatole.latestmovies.ui.theme.LatestMoviesTheme
import com.devmanishpatole.latestmovies.ui.utils.TestTag
import com.devmanishpatole.latestmovies.utils.FakeData.getFakeMovie
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

/**
 * @author Manish Patole, contact@devmanishpatole.com
 * @since 03/07/22
 */
class MovieRowTest {
    @get:Rule
    var uiTestRule = createComposeRule()

    private val movie = getFakeMovie()

    @Test
    fun testMovieDetailsDisplayed() {
        uiTestRule.setContent {
            LatestMoviesTheme {
                MovieRow(movie) {}
            }
        }

        uiTestRule.onNodeWithText(movie.title!!).assertIsDisplayed()
        uiTestRule.onNodeWithText(movie.overview!!).assertIsDisplayed()
    }

    @Test
    fun testMovieClicked() {
        var clicked = false
        uiTestRule.setContent {
            LatestMoviesTheme {
                MovieRow(movie) {
                    clicked = true
                }
            }
        }

        uiTestRule.onNodeWithTag(TestTag.MOVIE_ROW).performClick()
        assertTrue(clicked)
    }
}