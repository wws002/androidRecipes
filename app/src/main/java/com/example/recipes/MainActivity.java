package com.example.recipes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
    }

    void initialize(){
        findViewById(R.id.apps).setOnClickListener(this);
        findViewById(R.id.bfast).setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.apps:
                openApps();
                break;
            case R.id.bfast:
                openBreakfast();
                break;
            default:
                //this shouldn't happen
                break;
        }
    }

    void openApps(){
        Intent intent = new Intent(this, AppetizersActivity.class);
        startActivity(intent);
    }

    void openBreakfast(){
        Intent intent = new Intent(this, BreakfastActivity.class);
        startActivity(intent);
    }
}