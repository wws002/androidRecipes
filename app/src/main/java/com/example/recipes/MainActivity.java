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
    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.apps:
                openApps();
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
}