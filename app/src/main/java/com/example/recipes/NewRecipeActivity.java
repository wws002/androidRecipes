package com.example.recipes;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class NewRecipeActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newrecipe);
        initialize();
    }

    void initialize(){
        findViewById(R.id.saveBtn).setOnClickListener(this);
        findViewById(R.id.deleteBtn).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.saveBtn:
                saveRecipe();
                break;
            case R.id.deleteBtn:
                deleteRecipe();
                break;
            default:
                //this shouldn't happen
                break;
        }
    }

    void saveRecipe(){
        //get recipe type
        Intent oldIntent = getIntent();
        String type = oldIntent.getStringExtra("listItemType");

        //get user input
        EditText recipeName = findViewById(R.id.recipeName);
        String name = recipeName.getText().toString();
        EditText recipeContent = findViewById(R.id.recipe);
        String recipe = recipeContent.getText().toString();

        //add to database
        ContentValues myCV = new ContentValues();
        myCV.put(RecipeProvider.RECIPES_TABLE_COL_TYPE, type);
        myCV.put(RecipeProvider.RECIPES_TABLE_COL_TITLE, name);
        myCV.put(RecipeProvider.RECIPES_TABLE_COL_CONTENT, recipe);
        getContentResolver().insert(RecipeProvider.CONTENT_URI, myCV);

        //return to previous page
        if(type.equals("Appetizer")){
            Intent intent = new Intent(this, AppetizersActivity.class);
            startActivity(intent);
        }
        else if(type.equals("Breakfast")){
            Intent intent = new Intent(this, BreakfastActivity.class);
            startActivity(intent);
        }
    }

    void deleteRecipe(){
        //just return to the appetizers class
        Intent oldIntent = getIntent();
        String type = oldIntent.getStringExtra("listItemType");

        if(type.equals("Appetizer")) {
            Intent intent = new Intent(this, AppetizersActivity.class);
            startActivity(intent);
        }
        else if(type.equals("Breakfast")){
            Intent intent = new Intent(this, BreakfastActivity.class);
            startActivity(intent);
        }
    }
}
