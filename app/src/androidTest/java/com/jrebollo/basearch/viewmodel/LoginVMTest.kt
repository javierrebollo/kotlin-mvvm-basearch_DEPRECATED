package com.jrebollo.basearch.viewmodel

import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jrebollo.basearch.data.network.ErrorResult
import com.jrebollo.basearch.data.repository.UserRepository
import com.jrebollo.basearch.ui.base.ErrorType
import com.jrebollo.basearch.ui.viewmodel.LoginVM
import com.jrebollo.basearch.ui.viewmodel.LoginVMFactory
import com.jrebollo.basearch.util.getValue
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
        userRepository = spyk<UserRepository>()
        loginVM = LoginVMFactory(userRepository).buildViewModel()
    }

    @Test
    fun checkFieldValidations() {
        loginVM.loadData()
        Log.e("LoginVMTest", "Value is: ${loginVM.enableLoginButton.value ?: false}")

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
    fun checkLogin() {
        every {
            userRepository.login(any(), any())
        } returns ErrorResult(Exception())

        loginVM.login()
        assertTrue(getValue(loginVM.errorNotifier) is ErrorType.LoginError)

    }
}
