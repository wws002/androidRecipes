package com.example.recipes;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.content.Context;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.text.TextUtils;
import android.util.Log;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class RecipeProvider extends ContentProvider {
    private static String LOGTAG = "RecipeProvider:";
    private static final String DBNAME = "RecipeDB";
    private static final String AUTHORITY = "com.example.recipes.recipeprovider";
    private static final String TABLE_NAME = "Recipes";
    public static final Uri CONTENT_URI =
            Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME);

    public static final String RECIPES_TABLE_COL_ID = "_id";
    public static final String RECIPES_TABLE_COL_TITLE = "TITLE";
    public static final String RECIPES_TABLE_COL_CONTENT = "CONTENT";

    private static final String SQL_CREATE_MAIN = "CREATE TABLE " +
            TABLE_NAME + " " +
            "(" +
            RECIPES_TABLE_COL_ID + "INTEGER PRIMARY KEY, " +
            RECIPES_TABLE_COL_TITLE + "TEXT," +
            RECIPES_TABLE_COL_CONTENT + "TEXT)";

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private MainDatabaseHelper mOpenHelper;

    public RecipeProvider(){
        sUriMatcher.addURI(AUTHORITY, TABLE_NAME, 1);
        sUriMatcher.addURI(AUTHORITY, TABLE_NAME + "/#", 2);
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new MainDatabaseHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(TABLE_NAME);

        switch(sUriMatcher.match(uri)){
            case 1:
                if(TextUtils.isEmpty(sortOrder)) sortOrder="_ID ASC";
                break;
            case 2:
                selection = selection + "_ID = " + uri.getLastPathSegment();
                break;
            default:
                Log.e(LOGTAG, "URI not recognized " + uri);
        }

        Cursor cursor = queryBuilder.query(mOpenHelper.getWritableDatabase(),projection,selection,selectionArgs,null,null,sortOrder);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        throw new UnsupportedOperationException("Not yet implemented.");
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        switch(sUriMatcher.match(uri)){
            case 1:
                break;
            default:
                Log.e(LOGTAG, "URI not recognized " + uri);
        }
        long id = mOpenHelper.getWritableDatabase().insert(TABLE_NAME, null, values);
        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse(CONTENT_URI+"/" + id);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        switch(sUriMatcher.match(uri)){
            case 2:
                String id = uri.getPathSegments().get(1);
                selection = RECIPES_TABLE_COL_ID + "=" + id +
                        (!TextUtils.isEmpty(selection) ? "AND (" + selection + ")" : "");
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        int deleteCount = mOpenHelper.getWritableDatabase().delete(
                TABLE_NAME,selection,selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return deleteCount;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        switch (sUriMatcher.match(uri)){
            case 1:
                break;
            case 2:
                String id = uri.getPathSegments().get(1);
                selection = RECIPES_TABLE_COL_ID + "=" + id +
                        (!TextUtils.isEmpty(selection) ?
                                "AND (" + selection + ")" : "");
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        int updateCount = mOpenHelper.getWritableDatabase().update(TABLE_NAME, values,
                selection,selectionArgs);
        getContext().getContentResolver().notifyChange(uri,null);
        return updateCount;
    }

    //Class for creating an instance of a SQLiteOpenHelper
    //Performs creation of the SQLite Database if none exists
    protected static final class MainDatabaseHelper extends SQLiteOpenHelper {
        /*
         * Instantiates an open helper for the provider's SQLite data repository
         * Do not do database creation and upgrade here.
         */
        MainDatabaseHelper(Context context) {
            super(context, DBNAME, null, 1);
        }

        /*
         * Creates the data repository. This is called when the provider attempts to open the
         * repository and SQLite reports that it doesn't exist.
         */
        public void onCreate(SQLiteDatabase db) {

            // Creates the main table
            db.execSQL(SQL_CREATE_MAIN);
        }

        public void onUpgrade(SQLiteDatabase db, int int1, int int2){
            db.execSQL("DROP TABLE IF EXISTS Recipes");
            onCreate(db);

        }
    }

}
