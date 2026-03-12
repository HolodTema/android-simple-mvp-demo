package com.terabyte.simple_mvp_demo.activity

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.terabyte.simple_mvp_demo.R
import com.terabyte.simple_mvp_demo.databinding.ActivityLoginBinding
import com.terabyte.simple_mvp_demo.mvp_contract.LoginContract
import com.terabyte.simple_mvp_demo.mvp_contract.LoginPresenterProvider
import com.terabyte.simple_mvp_demo.presenter.LoginPresenter

class LoginActivity : AppCompatActivity(), LoginContract.View {

    private lateinit var presenter: LoginContract.Presenter

    private lateinit var binding: ActivityLoginBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        configureWindowInsets()

        //create presenter and attach View to presenter
        presenter = LoginPresenterProvider.getInstance()
        presenter.onAttach(this)

        binding.editLogin.setText(presenter.login)
        binding.editPassword.setText(presenter.password)

        presenter.addLoginStateListener { loginState ->
            when (loginState) {
                is LoginContract.LoginState.Success -> {
                    binding.progressBar.visibility = View.INVISIBLE
                    binding.textLoginStatus.text = loginState.statusMessage
                    binding.textLoginStatus.setTextColor(Color.GREEN)
                }
                is LoginContract.LoginState.Failure -> {
                    binding.progressBar.visibility = View.INVISIBLE
                    binding.textLoginStatus.text = loginState.statusMessage
                    binding.textLoginStatus.setTextColor(Color.RED)
                }
                is LoginContract.LoginState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                LoginContract.LoginState.Idle -> {
                    binding.progressBar.visibility = View.INVISIBLE
                    binding.textLoginStatus.text = ""
                }
            }
        }

        binding.buttonLogin.setOnClickListener {
            val login = binding.editLogin.text.toString()
            val password = binding.editPassword.text.toString()
            presenter.onLoginButtonClicked(login, password)
        }


    }

    override fun onStop() {
        super.onStop()
        presenter.login = binding.editLogin.text.toString()
        presenter.password = binding.editPassword.text.toString()
    }

    override fun onDestroy() {
        //to avoid memory leaks and dead view updates
        //we detach our view from presenter
        presenter.onDetach()

        if (isFinishing) {
            LoginPresenterProvider.recycleInstance()
        }
        super.onDestroy()
    }

    private fun configureWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}