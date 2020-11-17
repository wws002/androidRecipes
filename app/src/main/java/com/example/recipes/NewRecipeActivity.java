package com.example.recipes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class NewRecipeActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newrecipe);
        initialize();
    }

    void initialize(){
        findViewById(R.id.homeBtn).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.homeBtn:
                saveRecipe();
                break;
            default:
                //this shouldn't happen
                break;
        }
    }

    void saveRecipe(){
        Intent intent = new Intent(this, AppetizersActivity.class);
        startActivity(intent);
    }
}
