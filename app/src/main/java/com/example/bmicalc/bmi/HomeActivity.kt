package com.example.bmicalc.bmi

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.bmicalc.NavActivity
import com.example.bmicalc.R

class HomeActivity : AppCompatActivity() {

    private var height: EditText? = null
    private var weight: EditText? = null
    private var age: EditText? = null
    private var resultBmi: TextView? = null
    private var resultKcal: TextView? = null
    var radioButton: RadioButton? = null



    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val radioGroup = findViewById<RadioGroup>(R.id.radioGroup)

        height = findViewById(R.id.height)
        weight = findViewById(R.id.weight)
        age = findViewById(R.id.age)
        resultBmi = findViewById(R.id.resultBmi)
        resultKcal = findViewById(R.id.resultKcal)

        val buttonApply = findViewById<Button>(R.id.calc)
        buttonApply.setOnClickListener { v ->
            var radioId = radioGroup.getCheckedRadioButtonId()
            radioButton = findViewById(radioId)
            calculateBMI(v)
        }

        val backButton = findViewById<Button>(R.id.backButton)
        backButton.setOnClickListener {
            val i = Intent(this@HomeActivity, NavActivity::class.java)
            startActivity(i)
            finish()
        }
    }

    private fun calculateBMI(v: View) {
        val heightStr = height!!.text.toString()
        val weightStr = weight!!.text.toString()

        if (heightStr != null && "" != heightStr
                && weightStr != null && "" != weightStr) {
            val heightValue = heightStr.toFloat() / 100
            val weightValue = weightStr.toFloat()

            val bmi = weightValue / (heightValue * heightValue)
            showResult(displayBMI(bmi), calculateKcal())
        }
    }

    private fun calculateKcal(): String {

        val heightStr = height!!.text.toString()
        val weightStr = weight!!.text.toString()
        val gender = radioButton!!.text.toString()
        val aged = age!!.text.toString()
        var kcal = 0.0

        if (heightStr != null && "" != heightStr
                && weightStr != null
                && "" != weightStr
                && aged != null
                && "" != aged) {

            val heightValue = heightStr.toFloat()
            val weightValue = weightStr.toFloat()
            val ageValue = aged.toFloat()
            if (gender == "Male") {

                kcal = 66.47 + 13.7 * weightValue + 5.0 * heightValue - 6.76 * ageValue
            } else {
                kcal = 655.1 + 9.567 * weightValue + 1.85 * heightValue - 4.68 * ageValue
            }
        }

        return Math.round(kcal).toString()
    }

    private fun displayBMI(bmi: Float): Result {
        var bmiLabel = ""
        val amount: Int

        if (compareValues(bmi, 15f) <= 0) {
            bmiLabel = getString(R.string.very_severely_underweight)
            amount = 200
        } else if (compareValues(bmi, 15f) > 0 && compareValues(bmi, 16f) <= 0) {
            bmiLabel = getString(R.string.severely_underweight)
            amount = 190
        } else if (compareValues(bmi, 16f) > 0 && compareValues(bmi, 18.5f) <= 0) {
            bmiLabel = getString(R.string.underweight)
            amount = 180
        } else if (compareValues(bmi, 18.5f) > 0 && compareValues(bmi, 25f) <= 0) {
            bmiLabel = getString(R.string.normal)
            amount = 150
        } else if (compareValues(bmi, 25f) > 0 && compareValues(bmi, 30f) <= 0) {
            bmiLabel = getString(R.string.overweight)
            amount = 140
        } else if (compareValues(bmi, 30f) > 0 && compareValues(bmi, 35f) <= 0) {
            bmiLabel = getString(R.string.obese_class_i)
            amount = 130
        } else if (compareValues(bmi, 35f) > 0 && compareValues(bmi, 40f) <= 0) {
            bmiLabel = getString(R.string.obese_class_ii)
            amount = 120
        } else {
            bmiLabel = getString(R.string.obese_class_iii)
            amount = 110
        }

        return Result(Math.round(bmi).toFloat(), bmiLabel, amount)
    }

    private fun showResult(result: Result, kcal: String) {
        val i = Intent(this, ResultActivity::class.java)
        i.putExtra("bmi", result.BMI.toString())
        i.putExtra("kcal", kcal)
        i.putExtra("description", result.description)
        i.putExtra("amount", result.amount.toString())
        startActivity(i)
        finish()
    }

}