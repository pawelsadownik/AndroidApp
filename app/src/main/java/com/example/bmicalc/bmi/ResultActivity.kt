package com.example.bmicalc.bmi

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.bmicalc.R

class ResultActivity : AppCompatActivity() {

    private var resultBmi: TextView? = null
    private var resultKcal: TextView? = null
    private var resultDescription: TextView? = null
    private var rice: TextView? = null
    private var riceAmount: TextView? = null
    private var chicken: TextView? = null
    private var chickenAmount: TextView? = null
    private var vegetables: TextView? = null
    private var vegetablesAmount: TextView? = null
    private var dessert: TextView? = null
    private var dessertAmount: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val bmi = intent.getStringExtra("bmi")
        val kcal = intent.getStringExtra("kcal")
        val description = intent.getStringExtra("description")
        val amount = intent.getStringExtra("amount")

        resultBmi = findViewById(R.id.resultBmi) as TextView
        resultKcal = findViewById(R.id.resultKcal) as TextView
        resultDescription = findViewById(R.id.resultDescription) as TextView
        rice = findViewById(R.id.rice) as TextView
        riceAmount = findViewById(R.id.riceAmount) as TextView
        chicken = findViewById(R.id.chicken) as TextView
        chickenAmount = findViewById(R.id.chickenAmount) as TextView
        vegetables = findViewById(R.id.vegetables) as TextView
        vegetablesAmount = findViewById(R.id.vegetablesAmount) as TextView
        dessert = findViewById(R.id.dessert) as TextView
        dessertAmount = findViewById(R.id.dessertAmount) as TextView

        resultKcal!!.setText(kcal.toString())
        resultBmi!!.setText(bmi.toString())
        resultDescription!!.setText(description)

        setVisibility(false)

        val buttonApply = findViewById<Button>(R.id.recipes)
        buttonApply.setOnClickListener(View.OnClickListener {
            resultBmi!!.text = bmi
            resultKcal!!.setText(kcal)
            resultDescription!!.setText(description)
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

    fun setVisibility(isVisible: Boolean) {
        var visible = View.INVISIBLE

        if (isVisible) {
            visible = View.VISIBLE
        }

        rice!!.setVisibility(visible)
        riceAmount!!.setVisibility(visible)
        chicken!!.setVisibility(visible)
        chickenAmount!!.setVisibility(visible)
        vegetables!!.setVisibility(visible)
        riceAmount!!.setVisibility(visible)
        vegetablesAmount!!.setVisibility(visible)
        dessert!!.setVisibility(visible)
        dessertAmount!!.setVisibility(visible)
    }

    fun setRecipe(amount: String) {

        riceAmount!!.setText(amount)
        chickenAmount!!.setText((Integer.parseInt(amount) * 2).toString())
    }
}