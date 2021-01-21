package com.example.recipes;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class BreakfastActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breakfast);
        initialize();
    }

    void initialize(){
        //set up buttons
        findViewById(R.id.homeBtn).setOnClickListener(this);
        findViewById(R.id.newRecipeBtn).setOnClickListener(this);
        ListView listView = findViewById(R.id.breakfastList);
        listView.setOnItemClickListener((parent, view, position, id) -> editRecipe(id));

        //create projection for database query
        String[] projection = {
                RecipeProvider.RECIPES_TABLE_COL_ID,
                RecipeProvider.RECIPES_TABLE_COL_TITLE,
                RecipeProvider.RECIPES_TABLE_COL_CONTENT};

        //query database and fill list view
        Cursor myCursor = getContentResolver().query(RecipeProvider.CONTENT_URI, projection, RecipeProvider.RECIPES_TABLE_COL_TYPE + " = 'Breakfast'", null, null);
        ListView lvRecipes = findViewById(R.id.breakfastList);
        RecipesCursorAdapter recipesCursorAdapter = new RecipesCursorAdapter(this, myCursor);
        lvRecipes.setAdapter(recipesCursorAdapter);
    }

    @Override
    public void onClick(View v){
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
        intent.putExtra("listItemType", "Breakfast");
        startActivity(intent);
    }

    void editRecipe(long id){
        Intent intent = new Intent(this, EditRecipeActivity.class);
        intent.putExtra("listItemID", id);
        intent.putExtra("listItemType", "Breakfast");
        startActivity(intent);
    }
}
