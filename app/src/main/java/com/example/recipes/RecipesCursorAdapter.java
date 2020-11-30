package com.example.recipes;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class RecipesCursorAdapter extends CursorAdapter {
    RecipesCursorAdapter(Context context, Cursor cursor) { super(context, cursor, 0);}

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.item_recipe, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView textViewTitle = view.findViewById(R.id.recipeTitle);
        String title = cursor.getString(cursor.getColumnIndexOrThrow("TITLE"));
        textViewTitle.setText(title);
    }
}
