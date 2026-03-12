package com.terabyte.simple_mvp_demo.mvp_contract

interface LoginContract {

    interface View

    interface Presenter {
        var login: String
        var password: String

        fun onAttach(view: View)

        fun onDetach()

        fun onLoginButtonClicked(login: String, password: String)

        fun addLoginStateListener(listener: (LoginState) -> Unit)

        fun removeLoginStateListener()
    }

    sealed class LoginState {
        data class Success(val statusMessage: String) : LoginState()
        data class Failure(val statusMessage: String) : LoginState()
        data object Loading : LoginState()
        data object Idle : LoginState()
    }
}