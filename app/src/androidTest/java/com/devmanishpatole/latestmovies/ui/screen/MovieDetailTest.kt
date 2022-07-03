package com.devmanishpatole.latestmovies.ui.screen

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onLast
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.devmanishpatole.core.utils.hasDrawable
import com.devmanishpatole.latestmovies.R.drawable
import com.devmanishpatole.latestmovies.ui.theme.LatestMoviesTheme
import com.devmanishpatole.latestmovies.utils.FakeData
import com.devmanishpatole.latestmovies.viewmodels.DataSharingViewModel
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * @author Manish Patole, contact@devmanishpatole.com
 * @since 03/07/22
 */
class MovieDetailTest {
    @get:Rule
    var uiTestRule = createComposeRule()

    private val movie = FakeData.getFakeMovie()
    private val viewModel = DataSharingViewModel()

    @Before
    fun setup() {
        viewModel.movie = movie
    }

    @Test
    fun testMovieDetailsDisplayed() {
        with(uiTestRule) {
            setContent {
                LatestMoviesTheme {
                    MovieDetail(viewModel) {}
                }
            }

            // 2 UI nodes displaying title, action bar and detail page
            onAllNodes(hasText(movie.title!!)).assertCountEquals(2)
            onAllNodes(hasText(movie.title!!)).onFirst().assertIsDisplayed()
            onAllNodes(hasText(movie.title!!)).onLast().assertIsDisplayed()

            onNodeWithText(movie.overview!!).assertIsDisplayed()
            onNodeWithText(movie.releaseDate!!).assertIsDisplayed()
            onNodeWithText("${movie.voteAverage} / 10").assertIsDisplayed()

            onNode(hasDrawable(drawable.ic_rating)).assertIsDisplayed()
            onNode(hasDrawable(drawable.ic_calendar)).assertIsDisplayed()
        }
    }

    @Test
    fun testBackPress() {
        var backPressed = false

        uiTestRule.setContent {
            LatestMoviesTheme {
                MovieDetail(viewModel) {
                    backPressed = true
                }
            }
        }
        uiTestRule.onNode(hasDrawable(drawable.ic_back))
            .assertIsDisplayed()
            .assertHasClickAction()
            .performClick()

        assertTrue(backPressed)
    }
}
