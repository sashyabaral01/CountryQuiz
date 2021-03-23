package edu.uga.cs.countryquiz;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class QuizDBHelper extends SQLiteOpenHelper {

    public static final String TABLE_COUNTRIES="countries";
    public static final String COUNTRIES_COLUMN_ID = "_id";
    public static final String COUNTRIES_COLUMN_NAME = "name";
    public static final String COUNTRIES_COLUMN_CONTINENT = "continent";
    private static final String DB_NAME = "quiz.db" ;
    private static final int DB_VERSION =  1;

    private QuizDBHelper(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }


    private static final String CREATE_QUIZ =
            "create table " + TABLE_COUNTRIES + " ("
                    + COUNTRIES_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COUNTRIES_COLUMN_NAME + " TEXT, "
                    + COUNTRIES_COLUMN_CONTINENT + " TEXT " + ")";



    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
