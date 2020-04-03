package com.example.bmicalc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 2000;
    private EditText height;
    private EditText weight;
    private EditText age;
    private TextView resultBmi;
    private TextView resultKcal;
    RadioGroup radioGroup;
    RadioButton radioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        radioGroup = findViewById(R.id.radioGroup);

        height = (EditText) findViewById(R.id.height);
        weight = (EditText) findViewById(R.id.weight);
        age = (EditText) findViewById(R.id.age);
        resultBmi = (TextView) findViewById(R.id.resultBmi);
        resultKcal = (TextView) findViewById(R.id.resultKcal);

        Button buttonApply = findViewById(R.id.calc);
        buttonApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int radioId = radioGroup.getCheckedRadioButtonId();
                radioButton = findViewById(radioId);
                calculateBMI(v);
            }
        });

        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, NavActivity.class);
                startActivity((i));
                finish();
            }
        });
    }

    public void checkButton(View v){
        int radioId = radioGroup.getCheckedRadioButtonId();

        radioButton = findViewById(radioId);
    }

    public void calculateBMI(View v) {
        String heightStr = height.getText().toString();
        String weightStr = weight.getText().toString();

        if (heightStr != null && !"".equals(heightStr)
                && weightStr != null  &&  !"".equals(weightStr)) {
            float heightValue = Float.parseFloat(heightStr) / 100;
            float weightValue = Float.parseFloat(weightStr);

            float bmi = weightValue / (heightValue * heightValue);
            showResult(displayBMI(bmi), calculateKcal());
        }
    }

    public String calculateKcal() {

        String heightStr = height.getText().toString();
        String weightStr = weight.getText().toString();
        String gender = radioButton.getText().toString();
        String aged = age.getText().toString();
        double kcal=0;

        if (heightStr != null && !"".equals(heightStr)
                && weightStr != null
                &&  !"".equals(weightStr)
                && aged != null
                &&  !"".equals(aged)) {

            float heightValue = Float.parseFloat(heightStr);
            float weightValue = Float.parseFloat(weightStr);
            float ageValue = Float.parseFloat(aged);
            if (gender.equals("Male")){

                kcal = 66.47 + 13.7*weightValue + 5.0*heightValue - 6.76*ageValue;
            } else {
                kcal = 655.1 + 9.567*weightValue + 1.85*heightValue - 4.68*ageValue;
            }
        }

        return String.valueOf(Math.round(kcal));
    }

    private Result displayBMI(float bmi) {
        String bmiLabel = "";
        int amount;

        if (Float.compare(bmi, 15f) <= 0) {
            bmiLabel = getString(R.string.very_severely_underweight);
            amount = 200;
        } else if (Float.compare(bmi, 15f) > 0  &&  Float.compare(bmi, 16f) <= 0) {
            bmiLabel = getString(R.string.severely_underweight);
            amount = 190;
        } else if (Float.compare(bmi, 16f) > 0  &&  Float.compare(bmi, 18.5f) <= 0) {
            bmiLabel = getString(R.string.underweight);
            amount = 180;
        } else if (Float.compare(bmi, 18.5f) > 0  &&  Float.compare(bmi, 25f) <= 0) {
            bmiLabel = getString(R.string.normal);
            amount = 150;
        } else if (Float.compare(bmi, 25f) > 0  &&  Float.compare(bmi, 30f) <= 0) {
            bmiLabel = getString(R.string.overweight);
            amount = 140;
        } else if (Float.compare(bmi, 30f) > 0  &&  Float.compare(bmi, 35f) <= 0) {
            bmiLabel = getString(R.string.obese_class_i);
            amount = 130;
        } else if (Float.compare(bmi, 35f) > 0  &&  Float.compare(bmi, 40f) <= 0) {
            bmiLabel = getString(R.string.obese_class_ii);
            amount = 120;
        } else {
            bmiLabel = getString(R.string.obese_class_iii);
            amount = 110;
        }

        Result result = new Result(Math.round(bmi), bmiLabel, amount);

        return result;
    }
    private void showResult(Result result, String kcal){
        Intent i = new Intent(this, ResultActivity.class);
        i.putExtra("bmi", String.valueOf(result.getBMI()));
        i.putExtra("kcal", kcal);
        i.putExtra("description", result.getDescription());
        i.putExtra("amount", String.valueOf(result.getAmount()));
        startActivity((i));
        finish();
    }
}
