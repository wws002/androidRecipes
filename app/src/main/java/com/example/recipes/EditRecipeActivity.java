package com.example.recipes;

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

        String[] projection = {
                RecipeProvider.RECIPES_TABLE_COL_ID,
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
        Intent oldIntent = getIntent();
        Long id = oldIntent.getLongExtra("listItemID", 0);

        int didWork = getContentResolver().delete(Uri.parse(RecipeProvider.CONTENT_URI + "/" + id), null, null);
        if(didWork == 1){
            Toast.makeText(getApplicationContext(), "Deleted Note", Toast.LENGTH_SHORT).show();
        }

        Intent intent = new Intent(this, AppetizersActivity.class);
        startActivity(intent);
    }
}

