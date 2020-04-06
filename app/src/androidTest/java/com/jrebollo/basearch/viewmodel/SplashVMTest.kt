package com.jrebollo.basearch.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jrebollo.basearch.R
import com.jrebollo.basearch.data.repository.UserRepository
import com.jrebollo.basearch.ui.viewmodel.SplashVM
import com.jrebollo.basearch.ui.viewmodel.SplashVMFactory
import com.jrebollo.basearch.util.getValue
import com.jrebollo.basearch.utils.NavigationCommand
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SplashVMTest {
    private lateinit var userRepository: UserRepository
    private lateinit var splashVM: SplashVM

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        userRepository = mockk<UserRepository>()
        splashVM = SplashVMFactory(userRepository).buildViewModel()
    }

    @Test
    fun checkUserLogged() {
        every {
            userRepository.isLogged
        } returns true

        splashVM.loadData()

        val actionId = (getValue<NavigationCommand>(splashVM.navigation) as NavigationCommand.To)
            .directions.actionId

        assertEquals(R.id.fromSplashToHome, actionId)
    }

    @Test
    fun checkUserNotLogged() {
        every {
            userRepository.isLogged
        } returns false

        splashVM.loadData()

        val actionId = (getValue<NavigationCommand>(splashVM.navigation) as NavigationCommand.To)
            .directions.actionId

        assertEquals(R.id.fromSplashToLogin, actionId)
    }
}
