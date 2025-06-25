package com.lukafenir.luciuslist.model

import com.lukafenir.luciuslist.persistence.ShoppingListRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ShoppingListViewModelLoadingTest {

    private lateinit var repository: ShoppingListRepository
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = mockk()
        // Set up default mock behavior to prevent init from failing
        coEvery { repository.loadItems() } returns emptyList()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state should be loading`() {
        val viewModel = ShoppingListViewModel(repository)

        // Then
        assertThat(viewModel.isLoading.value).isTrue()
        assertThat(viewModel.items).isEmpty()
    }

    @Test
    fun `init should load items and stop loading on success`() = runTest {
        // Given
        val mockItems = listOf(
            ShoppingItem(1, "Apples"),
            ShoppingItem(2, "Bread")
        )
        coEvery { repository.loadItems() } returns mockItems

        // When
        val viewModel = ShoppingListViewModel(repository)
        assertThat(viewModel.isLoading.value).isTrue()
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        assertThat(viewModel.isLoading.value).isFalse()
        assertEquals(2, viewModel.items.size)
        assertEquals("Apples", viewModel.items[0].name)
        assertEquals("Bread", viewModel.items[1].name)
        coVerify { repository.loadItems() }
    }
}