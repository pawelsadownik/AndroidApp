package com.example.bmicalc

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        val SPLASH_TIME_OUT = 3000

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        Handler().postDelayed({
            val homeIntent = Intent(this@MainActivity, NavActivity::class.java)
            startActivity(homeIntent)
            finish()
        }, SPLASH_TIME_OUT.toLong())
    }
}