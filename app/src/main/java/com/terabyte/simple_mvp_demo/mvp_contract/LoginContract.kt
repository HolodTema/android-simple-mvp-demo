package com.terabyte.simple_mvp_demo.mvp_contract

interface LoginContract {

    interface View {
        fun showProgress()

        fun hideProgress()

        fun showLoginSuccess()

        fun showLoginFailure()
    }

    interface Presenter {
        fun onAttach(view: View)

        fun onDetach()

        fun onLoginButtonClicked()
    }
    
}