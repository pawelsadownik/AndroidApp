package com.example.bmicalc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.example.bmicalc.bmi.HomeActivity;
import com.example.bmicalc.chart.ChartActivity;
import com.example.bmicalc.game.StartGameActivity;

public class NavActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav);

        Button chartButton = findViewById(R.id.chartButton);
        chartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(NavActivity.this, ChartActivity.class);
                startActivity((i));
                finish();
            }
        });

        Button bmiButton = findViewById(R.id.bmiButton);
        bmiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(NavActivity.this, HomeActivity.class);
                startActivity((i));
                finish();
            }
        });

        Button gameButton = findViewById(R.id.gameButton);
        gameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(NavActivity.this, StartGameActivity.class);
                startActivity((i));
                finish();
            }
        });
    }
}
