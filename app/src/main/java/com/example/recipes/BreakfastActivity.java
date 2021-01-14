package com.example.recipes;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class BreakfastActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breakfast);
        initialize();
    }

    void initialize(){
    }

    @Override
    public void onClick(View v){
    }
}
