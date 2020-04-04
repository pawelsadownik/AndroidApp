package com.example.bmicalc.bmi;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.bmicalc.R;

public class ResultActivity extends AppCompatActivity {

    private TextView resultBmi;
    private TextView resultKcal;
    private TextView resultDescription;
    private TextView rice;
    private TextView riceAmount;
    private TextView chicken;
    private TextView chickenAmount;
    private TextView vegetables;
    private TextView vegetablesAmount;
    private TextView dessert;
    private TextView dessertAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        final String bmi = getIntent().getStringExtra("bmi");
        final String kcal = getIntent().getStringExtra("kcal");
        final String description = getIntent().getStringExtra("description");
        final String amount = getIntent().getStringExtra("amount");

        resultBmi = (TextView) findViewById(R.id.resultBmi);
        resultKcal = (TextView) findViewById(R.id.resultKcal);
        resultDescription = (TextView) findViewById(R.id.resultDescription);
        rice = (TextView) findViewById(R.id.rice);
        riceAmount = (TextView) findViewById(R.id.riceAmount);
        chicken = (TextView) findViewById(R.id.chicken);
        chickenAmount = (TextView) findViewById(R.id.chickenAmount);
        vegetables = (TextView) findViewById(R.id.vegetables);
        vegetablesAmount = (TextView) findViewById(R.id.vegetablesAmount);
        dessert = (TextView) findViewById(R.id.dessert);
        dessertAmount = (TextView) findViewById(R.id.dessertAmount);

        resultKcal.setText(String.valueOf(kcal));
        resultBmi.setText(String.valueOf(bmi));
        resultDescription.setText(description);

        setVisibility(false);

        Button buttonApply = findViewById(R.id.recipes);
        buttonApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultBmi.setText(bmi);
                resultKcal.setText(kcal);
                resultDescription.setText(description);
                setRecipe( amount);
                setVisibility(true);
            }
        });

        Button buttonStart = findViewById(R.id.start);
        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ResultActivity.this, HomeActivity.class);
                startActivity((i));
                finish();
            }
        });
    }

    public void setVisibility(boolean isVisible){
        int visible = View.INVISIBLE;

        if (isVisible){
            visible = View.VISIBLE;
        }

        rice.setVisibility(visible);
        riceAmount.setVisibility(visible);
        chicken.setVisibility(visible);
        chickenAmount.setVisibility(visible);
        vegetables.setVisibility(visible);
        riceAmount.setVisibility(visible);
        vegetablesAmount.setVisibility(visible);
        dessert.setVisibility(visible);
        dessertAmount.setVisibility(visible);
    }

    public void setRecipe(String amount){

        riceAmount.setText(amount);
        chickenAmount.setText(String.valueOf(Integer.parseInt(amount) * 2));
    }
}
