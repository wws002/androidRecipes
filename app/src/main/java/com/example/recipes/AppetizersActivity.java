package com.example.recipes;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class AppetizersActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appetizers);
        initialize();
    }

    void initialize(){
        findViewById(R.id.homeBtn).setOnClickListener(this);
        findViewById(R.id.newRecipeBtn).setOnClickListener(this);

        String[] projection = {
                RecipeProvider.RECIPES_TABLE_COL_ID,
                RecipeProvider.RECIPES_TABLE_COL_TITLE,
                RecipeProvider.RECIPES_TABLE_COL_CONTENT};

        Cursor myCursor = getContentResolver().query(RecipeProvider.CONTENT_URI, projection, null, null, null);
        ListView lvRecipes = findViewById(R.id.appsList);
        RecipesCursorAdapter recipesCursorAdapter = new RecipesCursorAdapter(this, myCursor);
        lvRecipes.setAdapter(recipesCursorAdapter);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.homeBtn:
                returnHome();
                break;
            case R.id.newRecipeBtn:
                newRecipe();
                break;
            default:
                //this shouldn't happen
                break;
        }
    }

    void returnHome(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    void newRecipe(){
        Intent intent = new Intent(this, NewRecipeActivity.class);
        startActivity(intent);
    }
}
