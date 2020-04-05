package com.jrebollo.basearch.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jrebollo.basearch.DependencyInjector
import com.jrebollo.basearch.R
import com.jrebollo.basearch.data.network.ErrorResult
import com.jrebollo.basearch.data.network.SuccessResult
import com.jrebollo.basearch.data.repository.UserRepository
import com.jrebollo.basearch.ui.base.ErrorType
import com.jrebollo.basearch.ui.viewmodel.LoginVM
import com.jrebollo.basearch.ui.viewmodel.LoginVMFactory
import com.jrebollo.basearch.util.getValue
import com.jrebollo.basearch.utils.NavigationCommand
import io.mockk.every
import io.mockk.spyk
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class LoginVMTest {
    private lateinit var userRepository: UserRepository
    private lateinit var loginVM: LoginVM

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        userRepository = DependencyInjector.provideUserRepository()
        loginVM = LoginVMFactory(userRepository).buildViewModel()
    }

    @Test
    fun checkFieldValidations() {
        loginVM.loadData()

        assertNull(getValue(loginVM.enableLoginButton))

        loginVM.usernameLiveData.value = "Pepe"

        assertFalse(loginVM.enableLoginButton.value!!)

        loginVM.usernameLiveData.value = ""
        loginVM.passwordLiveData.value = "pass"

        assertFalse(loginVM.enableLoginButton.value!!)

        loginVM.usernameLiveData.value = "Pepe"
        loginVM.passwordLiveData.value = "pass"

        assertTrue(loginVM.enableLoginButton.value!!)
    }

    @Test
    fun checkLoginError() {
        val spy = spyk(userRepository)
        every {
            spy.login(any(), any())
        } returns ErrorResult(Exception())

        loginVM.login()
        assertTrue(getValue(loginVM.errorNotifier) is ErrorType.LoginError)
    }

    @Test
    fun checkLoginSuccess() {
        val spyUserRepository = spyk(userRepository)
        every {
            spyUserRepository.login(any(), any())
        } returns SuccessResult("")

        loginVM.login()

        val actionId = (getValue<NavigationCommand>(loginVM.navigation) as NavigationCommand.To)
            .directions.actionId

        assertEquals(R.id.fromLoginToHome, actionId)
    }
}
