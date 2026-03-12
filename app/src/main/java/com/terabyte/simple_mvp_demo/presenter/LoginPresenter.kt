package com.terabyte.simple_mvp_demo.presenter

import com.terabyte.simple_mvp_demo.model.LoginRepository
import com.terabyte.simple_mvp_demo.mvp_contract.LoginContract
import kotlin.math.log

class LoginPresenter() : LoginContract.Presenter {
    private var view: LoginContract.View? = null


    private val loginRepository = LoginRepository()


    private var loginState: LoginContract.LoginState = LoginContract.LoginState.Idle
    private var loginStateListener: ((LoginContract.LoginState) -> Unit)? = null


    override var login: String = ""
    override var password: String = ""


    override fun onAttach(view: LoginContract.View) {
        this.view = view
    }


    override fun onLoginButtonClicked(login: String, password: String) {
        //simple validation we can make right here
        if (login.isBlank() || password.isBlank()) {
            loginState = LoginContract.LoginState.Failure("Login and password fields cannot be empty")
            loginStateListener?.invoke(loginState)
            return
        }

        loginState = LoginContract.LoginState.Loading
        loginStateListener?.invoke(loginState)

        //if validation is done, Presenter launches model's method
        loginRepository.tryLogin(login, password) { isSuccessful, statusMessage ->
            if (isSuccessful) {
                loginState = LoginContract.LoginState.Success(statusMessage)
                loginStateListener?.invoke(loginState)
            } else {
                loginState = LoginContract.LoginState.Failure(statusMessage)
                loginStateListener?.invoke(loginState)
            }
        }
    }


    override fun addLoginStateListener(listener: (LoginContract.LoginState) -> Unit) {
        loginStateListener = listener
        loginStateListener?.invoke(loginState)
    }


    override fun removeLoginStateListener() {
        loginStateListener = null
    }


    override fun onDetach() {
        view = null
    }
}