package com.devmanishpatole.latestmovies.paging

import androidx.paging.PagingSource
import androidx.paging.PagingSource.LoadParams.Append
import androidx.paging.PagingSource.LoadResult.Page
import androidx.paging.PagingState
import com.devmanishpatole.core.repositories.Either
import com.devmanishpatole.latestmovies.repositories.MoviesRepository
import com.devmanishpatole.network.models.Movie
import com.devmanishpatole.network.models.MoviesResponse
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

/**
 * @author Manish Patole, contact@devmanishpatole.com
 * @since 03/07/22
 */
@ExperimentalCoroutinesApi
class LatestMoviesPagingSourceTest {

    @MockK
    lateinit var repository: MoviesRepository

    lateinit var pagingSource: LatestMoviesPagingSource

    private val testDispatcher = UnconfinedTestDispatcher()

    companion object {
        private const val PAGE_SIZE = 40
        private const val ANCHOR_POSITION = 10
        private const val PREVIOUS_KEY = 2
        private const val NEXT_KEY = 4
    }

    @BeforeEach
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        MockKAnnotations.init(this)
        pagingSource = LatestMoviesPagingSource(repository)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
        clearAllMocks()
    }

    @Nested
    @DisplayName("When fetching refresh key")
    inner class RefreshKey {

        @Nested
        @DisplayName("And no access to paging data established")
        inner class NoAnchorPosition {

            @Test
            fun `Then should not provide refresh key`() {
                val state = mockk<PagingState<Int, Movie>>()
                every { state.anchorPosition } returns null
                assertNull(pagingSource.getRefreshKey(state = state))
            }
        }

        @Nested
        @DisplayName("And access to paging data established")
        inner class AnchorPosition {
            @Nested
            @DisplayName("And previous page having more data")
            inner class PreviousPage {

                @Test
                fun `Then should provide refresh key to load data from previous page`() {
                    val state = mockk<PagingState<Int, Movie>>()
                    val page = mockk<PagingSource.LoadResult.Page<Int, Movie>>()
                    every { state.anchorPosition } returns ANCHOR_POSITION
                    every { state.closestPageToPosition(ANCHOR_POSITION) } returns page
                    every { page.prevKey } returns PREVIOUS_KEY

                    assertEquals(
                        PREVIOUS_KEY + 1,
                        pagingSource.getRefreshKey(state = state)
                    )
                }
            }

            @Nested
            @DisplayName("And next page having more data")
            inner class NextPage {

                @Test
                fun `Then should provide refresh key to load data from next page`() {
                    val state = mockk<PagingState<Int, Movie>>()
                    val page = mockk<PagingSource.LoadResult.Page<Int, Movie>>()
                    every { state.anchorPosition } returns ANCHOR_POSITION
                    every { state.closestPageToPosition(ANCHOR_POSITION) } returns page
                    every { page.prevKey } returns null
                    every { page.nextKey } returns NEXT_KEY

                    assertEquals(NEXT_KEY - 1, pagingSource.getRefreshKey(state = state))
                }
            }
        }
    }

    @Nested
    @DisplayName("When loading movies")
    inner class LoadData {

        @Nested
        @DisplayName("And having movies data more than page size")
        inner class LoadMore {

            @Test
            fun `Then should load movies with reference to next page`() {
                runBlocking(testDispatcher) {
                    val movieResponse = mockk<MoviesResponse>()
                    every { movieResponse.movies } returns listOf(mockk(), mockk())
                    every { movieResponse.totalPages } returns 100

                    coEvery { repository.getLatestMovies(any()) } returns Either.success(
                        movieResponse
                    )

                    val result =
                        pagingSource.load(
                            Append(1, PAGE_SIZE, true)
                        )

                    assertTrue(result is Page)
                    val page = (result as Page)
                    assertEquals(2, page.data.size)
                    assertNotNull(page.nextKey)
                }
            }
        }

        @Nested
        @DisplayName("And having data less than page size")
        inner class LoadLess {

            @Test
            fun `Then should load movies without reference to next page`() {
                runBlocking(testDispatcher) {
                    val movieResponse = mockk<MoviesResponse>()
                    every { movieResponse.movies } returns listOf(mockk(), mockk())
                    every { movieResponse.totalPages } returns 5

                    coEvery { repository.getLatestMovies(any()) } returns Either.success(
                        movieResponse
                    )

                    val result =
                        pagingSource.load(
                            Append(5, PAGE_SIZE, true)
                        )

                    assertTrue(result is Page)
                    val page = (result as Page)
                    assertEquals(2, page.data.size)
                    assertNull(page.nextKey)
                }
            }
        }

        @Nested
        @DisplayName("And API failure")
        inner class Error {

            @Test
            fun `Then should return error state`() {
                runBlocking(testDispatcher) {
                    coEvery {
                        repository.getLatestMovies(any())
                    } throws Exception("Failed to load movies")

                    val result =
                        pagingSource.load(
                            Append(1, PAGE_SIZE, true)
                        )

                    assertTrue(result is PagingSource.LoadResult.Error)
                    val error = (result as PagingSource.LoadResult.Error)
                    assertEquals(error.throwable.message, "Failed to load movies")
                }
            }
        }
    }
}