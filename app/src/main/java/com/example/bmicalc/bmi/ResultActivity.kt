package com.example.bmicalc.bmi

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.bmicalc.R

class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val bmi = intent.getStringExtra("bmi")
        val kcal = intent.getStringExtra("kcal")
        val description = intent.getStringExtra("description")
        val amount = intent.getStringExtra("amount")

        findViewById<TextView>(R.id.resultKcal).setText(kcal.toString())
        findViewById<TextView>(R.id.resultBmi).setText(bmi.toString())
        findViewById<TextView>(R.id.resultDescription).setText(description)

        setVisibility(false)

        val buttonApply = findViewById<Button>(R.id.recipes)
        buttonApply.setOnClickListener(View.OnClickListener {
            setRecipe(amount)
            setVisibility(true)
        })

        val buttonStart = findViewById<Button>(R.id.start)
        buttonStart.setOnClickListener(View.OnClickListener {
            val i = Intent(this@ResultActivity, HomeActivity::class.java)
            startActivity(i)
            finish()
        })
    }

    private fun setVisibility(isVisible: Boolean) {
        var visible = View.INVISIBLE

        if (isVisible) {
            visible = View.VISIBLE
        }

        findViewById<TextView>(R.id.rice).visibility = visible
        findViewById<TextView>(R.id.riceAmount).visibility = visible
        findViewById<TextView>(R.id.chicken).visibility = visible
        findViewById<TextView>(R.id.chickenAmount).visibility = visible
        findViewById<TextView>(R.id.vegetables).visibility = visible
        findViewById<TextView>(R.id.vegetablesAmount).visibility = visible
        findViewById<TextView>(R.id.dessert).visibility = visible
        findViewById<TextView>(R.id.dessertAmount).visibility = visible

    }

    private fun setRecipe(amount: String) {

        findViewById<TextView>(R.id.riceAmount).text =  amount
        findViewById<TextView>(R.id.chickenAmount).text = (Integer.parseInt(amount) * 2).toString()
    }
}