package com.example.bmicalc

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.bmicalc.bmi.HomeActivity
import com.example.bmicalc.chart.ChartActivity
import com.example.bmicalc.game.StartGameActivity

class NavActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_nav)

        val chartButton = findViewById<Button>(R.id.chartButton)
        chartButton.setOnClickListener(View.OnClickListener {
            val i = Intent(this, ChartActivity::class.java)
            startActivity(i)
            finish()
        })

        val bmiButton = findViewById<Button>(R.id.bmiButton)
        bmiButton.setOnClickListener {
            val i = Intent(this, HomeActivity::class.java)
            startActivity(i)
            finish()
        }

        val gameButton = findViewById<Button>(R.id.gameButton)
        gameButton.setOnClickListener(View.OnClickListener {
            val i = Intent(this, StartGameActivity::class.java)
            startActivity(i)
            finish()
        })
    }
}