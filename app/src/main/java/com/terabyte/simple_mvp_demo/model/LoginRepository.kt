package com.terabyte.simple_mvp_demo.model

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginRepository {

    //here callback has two params
    //boolean - isSuccessful
    //string - status text
    fun tryLogin(login: String, password: String, callback: (Boolean, String) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            //imitate time-requiring-work
            delay(3000)

            val result: Pair<Boolean, String> = if (login == "admin" && password == "admin") {
                true to "Welcome, admin! Login success!"
            }
            else {
                false to "Invalid login or password"
            }

            withContext(Dispatchers.Main) {
                callback(result.first, result.second)
            }
        }
    }
}