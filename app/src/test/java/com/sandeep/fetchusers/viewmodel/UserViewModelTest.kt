package com.sandeep.fetchusers.viewmodel

import com.sandeep.fetchusers.data.UserRepository
import com.sandeep.fetchusers.data.model.User
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

@ExperimentalCoroutinesApi
class UserViewModelTest {

    private lateinit var viewModel: UserViewModel
    private lateinit var userRepository: UserRepository

    private var testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        userRepository = mock()
        viewModel = UserViewModel(userRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `fetch users should emit loading state initially`() = runTest {
        val users = listOf(User(1, 20, "John"))
        `when`(userRepository.getUsers()).thenReturn(users)

        viewModel.fetchUsers()
        assertEquals(Status.Loading, viewModel.usersStatus.value)
    }

    @Test
    fun `fetch users should emit Success state when data is fetched successfully`() = runTest {
        val users = listOf(User(1, 20, "John"))
        `when`(userRepository.getUsers()).thenReturn(users)

        viewModel.fetchUsers()
        testDispatcher.scheduler.advanceUntilIdle()
        assertEquals(Status.Success(users), viewModel.usersStatus.value)
        verify(userRepository).getUsers()
    }

    @Test
    fun `fetch users should emit error state when an exception occurs`() = runTest {
        val errorMessage = "Network Error"
        `when`(userRepository.getUsers()).thenThrow(RuntimeException(errorMessage))

        viewModel.fetchUsers()
        testDispatcher.scheduler.advanceUntilIdle()
        assertEquals(Status.Error(errorMessage), viewModel.usersStatus.value)
        verify(userRepository).getUsers()
    }

}