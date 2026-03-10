package com.terabyte.simple_mvp_demo.mvp_contract

interface LoginContract {

    interface View {
        fun showProgress()

        fun hideProgress()

        fun showLoginSuccess(statusMessage: String)

        fun showLoginFailure(statusMessage: String)
    }

    interface Presenter {
        fun onAttach(view: View)

        fun onDetach()

        fun onLoginButtonClicked(login: String, password: String)
    }

}