package com.example.recipes;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditRecipeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editrecipe);

        Intent oldIntent = getIntent();
        Long id = oldIntent.getLongExtra("listItemID", 0);
        String type = oldIntent.getStringExtra("listItemType");

        String[] projection = {
                RecipeProvider.RECIPES_TABLE_COL_ID,
                RecipeProvider.RECIPES_TABLE_COL_TYPE,
                RecipeProvider.RECIPES_TABLE_COL_TITLE,
                RecipeProvider.RECIPES_TABLE_COL_CONTENT};

        Cursor myCursor = getContentResolver().query(RecipeProvider.CONTENT_URI, projection, "_id = " + id.toString(), null, null);

        myCursor.moveToFirst();
        int index = myCursor.getColumnIndexOrThrow("TITLE");
        String title = myCursor.getString(index);
        index = myCursor.getColumnIndexOrThrow("CONTENT");
        String content = myCursor.getString(index);

        EditText textTitle = findViewById(R.id.editRecipeTitle);
        EditText textContent = findViewById(R.id.editRecipeContent);

        textTitle.setText(title);
        textContent.setText(content);
    }

    public void deleteEditedNote(View v){
        //get recipe ID
        Intent oldIntent = getIntent();
        Long id = oldIntent.getLongExtra("listItemID", 0);

        //get recipe type
        String type = oldIntent.getStringExtra("listItemType");

        //delete the note
        int didWork = getContentResolver().delete(Uri.parse(RecipeProvider.CONTENT_URI + "/" + id), null, null);
        if(didWork == 1){
            Toast.makeText(getApplicationContext(), "Deleted Note", Toast.LENGTH_SHORT).show();
        }

        //start previous activity
        if(type.equals("Appetizer")) {
            Intent intent = new Intent(this, AppetizersActivity.class);
            startActivity(intent);
        }
        else if(type.equals("Breakfast")){
            Intent intent = new Intent(this, BreakfastActivity.class);
            startActivity(intent);
        }
    }

    public void saveEditedNote(View v){
        //get recipe ID
        Intent oldIntent = getIntent();
        Long id = oldIntent.getLongExtra("listItemID", 0);

        //get recipe type
        String type = oldIntent.getStringExtra("listItemType");

        //get user input
        EditText recipeTitle = findViewById(R.id.editRecipeTitle);
        EditText recipeContent = findViewById(R.id.editRecipeContent);
        String title = recipeTitle.getText().toString();
        String content = recipeContent.getText().toString();

        //create content values
        ContentValues myCV = new ContentValues();
        myCV.put(RecipeProvider.RECIPES_TABLE_COL_TITLE, title);
        myCV.put(RecipeProvider.RECIPES_TABLE_COL_CONTENT, content);

        //update the database
        getContentResolver().update(RecipeProvider.CONTENT_URI, myCV, "_id = " + id.toString(), null);

        //start previous activity
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
