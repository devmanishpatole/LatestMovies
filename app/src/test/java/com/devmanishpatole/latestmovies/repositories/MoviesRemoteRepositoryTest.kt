package com.devmanishpatole.latestmovies.repositories

import com.devmanishpatole.core.utils.NetworkManager
import com.devmanishpatole.network.models.MoviesResponse
import com.devmanishpatole.network.services.MoviesService
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
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail
import retrofit2.Response

/**
 * Behavior driven unit test for [MoviesRemoteRepository]
 *
 * @author Manish Patole, contact@devmanishpatole.com
 * @since 03/07/22
 */
@ExperimentalCoroutinesApi
class MoviesRemoteRepositoryTest {

    @MockK
    lateinit var service: MoviesService

    @MockK
    lateinit var networkManager: NetworkManager

    lateinit var repository: MoviesRemoteRepository

    private val testDispatcher = UnconfinedTestDispatcher()

    @BeforeEach
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        MockKAnnotations.init(this)
        repository = MoviesRemoteRepository(service, networkManager)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
        clearAllMocks()
    }

    @Nested
    @DisplayName("When requesting latest movies")
    inner class GetMovies {

        @Nested
        @DisplayName("And connected to internet")
        inner class Connected {

            @BeforeEach
            fun setup() {
                every { networkManager.isConnected } returns true
            }

            private val moviesResponse = mockk<MoviesResponse>()
            private val response = mockk<Response<MoviesResponse>>()

            @Test
            @DisplayName("Then should provide latest movies")
            fun `should provide latest movies`() {
                coEvery { service.getNowPlayingMovies(any()) } returns response

                every { response.isSuccessful } returns true
                every { response.body() } returns moviesResponse
                every { moviesResponse.totalPages } returns 100

                runBlocking(testDispatcher) {
                    repository.getLatestMovies(1).onFailure {
                        fail("API should return result")
                    }.onSuccess {
                        assertEquals(100, it.totalPages)
                    }
                }
            }

            @Nested
            @DisplayName("And API fails to return result")
            inner class ApiFailure {
                @Test
                @DisplayName("Then should throw error")
                fun `should throw error`() {
                    coEvery { service.getNowPlayingMovies(any()) } throws Exception("Test exception")

                    runBlocking(testDispatcher) {
                        repository.getLatestMovies(1).onFailure {
                            assertEquals("Test exception", it)
                        }.onSuccess {
                            fail("API should fail")
                        }
                    }
                }
            }
        }

        @Nested
        @DisplayName("And not connected to internet")
        inner class NotConnected {

            @BeforeEach
            fun setup() {
                every { networkManager.isConnected } returns false
            }

            @Test
            @DisplayName("Then should throw no internet error")
            fun `should throw no internet error`() {
                runBlocking(testDispatcher) {
                    repository.getLatestMovies(1).onFailure {
                        assertEquals("Internet Unavailable", it)
                    }.onSuccess {
                        fail("API should fail")
                    }
                }
            }
        }
    }

    @Nested
    @DisplayName("When searching movie")
    inner class SearchMovie {
        @Nested
        @DisplayName("And connected to internet")
        inner class Connected {

            @BeforeEach
            fun setup() {
                every { networkManager.isConnected } returns true
            }

            private val moviesResponse = mockk<MoviesResponse>()
            private val response = mockk<Response<MoviesResponse>>()

            @Test
            @DisplayName("Then should provide searched result")
            fun `should provide searched movies`() {
                coEvery { service.searchMovies(any(), any()) } returns response

                every { response.isSuccessful } returns true
                every { response.body() } returns moviesResponse
                every { moviesResponse.totalPages } returns 100

                runBlocking(testDispatcher) {
                    repository.searchMovies("ince", 1).onFailure {
                        fail("API should return result")
                    }.onSuccess {
                        assertEquals(100, it.totalPages)
                    }
                }
            }


            @Nested
            @DisplayName("And API fails to return result")
            inner class ApiFailure {
                @Test
                @DisplayName("Then should throw error")
                fun `should throw error`() {
                    coEvery {
                        service.searchMovies(
                            any(),
                            any()
                        )
                    } throws Exception("Test exception")

                    runBlocking(testDispatcher) {
                        repository.searchMovies("ince", 1).onFailure {
                            assertEquals("Test exception", it)
                        }.onSuccess {
                            fail("API should fail")
                        }
                    }
                }
            }

        }

        @Nested
        @DisplayName("And not connected to internet")
        inner class NotConnected {

            @BeforeEach
            fun setup() {
                every { networkManager.isConnected } returns false
            }

            @Test
            @DisplayName("Then should throw no internet error")
            fun `should throw no internet error`() {
                runBlocking(testDispatcher) {
                    repository.searchMovies("ince", 1).onFailure {
                        assertEquals("Internet Unavailable", it)
                    }.onSuccess {
                        fail("API should fail")
                    }
                }
            }
        }
    }
}