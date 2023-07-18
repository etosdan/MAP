package com.azamovhudstc.quizapp.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import hr.vsite.ulaznitestovi.activity.LoginActivity
import hr.vsite.ulaznitestovi.local_data.TestPref
import hr.vsite.ulaznitestovi.ui.HomeActivity

class SplashScreen : AppCompatActivity() {
    private lateinit var quizPref: TestPref
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        quizPref = TestPref.getInstance()
        splashAndroid11()
    }

    fun splashAndroid11() {
        Handler().postDelayed({
            println(quizPref.name + " 123123123123123333333333333333")
            if (quizPref.name.isEmpty()) {
                startActivity(Intent(applicationContext, LoginActivity::class.java))
            } else {
                startActivity(Intent(applicationContext, HomeActivity::class.java))
            }
            finish()
        }, 1000)
    }

}