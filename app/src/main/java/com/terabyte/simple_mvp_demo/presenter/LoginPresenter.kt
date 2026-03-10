package com.terabyte.simple_mvp_demo.presenter

import com.terabyte.simple_mvp_demo.model.LoginRepository
import com.terabyte.simple_mvp_demo.mvp_contract.LoginContract

class LoginPresenter : LoginContract.Presenter {
    private var view: LoginContract.View? = null

    private val loginRepository = LoginRepository()

    override fun onAttach(view: LoginContract.View) {
        this.view = view
    }

    override fun onLoginButtonClicked(login: String, password: String) {
        //simple validation we can make right here
        if (login.isBlank() || password.isBlank()) {
            view?.showLoginFailure("Login and password fields cannot be empty")
            return
        }

        view?.showProgress()

        //if validation is done, Presenter launches model's method
        loginRepository.tryLogin(login, password) { isSuccessful, statusMessage ->
            view?.hideProgress()

            if (isSuccessful) {
                view?.showLoginSuccess(statusMessage)
            }
            else {
                view?.showLoginFailure(statusMessage)
            }
        }
    }

    override fun onDetach() {
        view = null
    }
}