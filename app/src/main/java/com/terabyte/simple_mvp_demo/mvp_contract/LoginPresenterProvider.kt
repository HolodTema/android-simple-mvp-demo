package com.terabyte.simple_mvp_demo.mvp_contract

import com.terabyte.simple_mvp_demo.presenter.LoginPresenter


object LoginPresenterProvider {
    private var instance: LoginContract.Presenter? = null

    fun getInstance(): LoginContract.Presenter {
        if (instance == null) {
            instance = LoginPresenter()
        }
        return instance!!
    }

    fun recycleInstance() {
        instance = null
    }
}