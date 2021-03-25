package edu.uga.cs.countryquiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class QuizDBHelper extends SQLiteOpenHelper {


    private static final String DB_NAME = "quiz.db";
    private static final int DATABASE_VERSION=1;


    private static final String TABLE_COUNTRY="Countries";
    private static final String COUNTRY_COLUMN_ID="Country_ID";
    private static final String COUNTRY_COLUMN_COUNTRY="Country";
    private static final String COUNTRY_COLUMN_CONTINENT="Continent";


    private static final String TABLE_RESULT="results";
    private static final String RESULT_COLUMN_ID="Result_ID";
    private static final String RESULT_SCORE="score";
    private static final String RESULT_DATE="date";
    private static final String RESULT_QUESTION_1="question1";
    private static final String RESULT_QUESTION_2="question2";
    private static final String RESULT_QUESTION_3="question3";
    private static final String RESULT_QUESTION_4="question4";
    private static final String RESULT_QUESTION_5="question5";
    private static final String RESULT_QUESTION_6="question6";
    private static final String RESULT_COMPLETED="completed";


    private static final String CREATE_TABLE_COUNTRY =
            "CREATE TABLE " + TABLE_COUNTRY + "("
                    + COUNTRY_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COUNTRY_COLUMN_COUNTRY + " TEXT,"
                    + COUNTRY_COLUMN_CONTINENT + " TEXT"
                    + ")";



    private static final String CREATE_TABLE_RESULT =
            "CREATE TABLE " + TABLE_RESULT + "("
                    + RESULT_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + RESULT_QUESTION_1 + " INTEGER,"
                    + RESULT_QUESTION_2 + " INTEGER,"
                    + RESULT_QUESTION_3 + " INTEGER,"
                    + RESULT_QUESTION_4 + " INTEGER,"
                    + RESULT_QUESTION_5 + " INTEGER,"
                    + RESULT_QUESTION_5 + " INTEGER,"
                    + RESULT_QUESTION_6 + " INTEGER,"
                    + RESULT_SCORE + " TEXT,"
                    + RESULT_DATE + " TEXT,"
                    + RESULT_COMPLETED +" TEXT,"
                    + "FOREIGN KEY(q1) REFERENCES TABLE_COUNTRY(id),"
                    + "FOREIGN KEY(q2) REFERENCES TABLE_COUNTRY(id),"
                    + "FOREIGN KEY(q3) REFERENCES TABLE_COUNTRY(id),"
                    + "FOREIGN KEY(q4) REFERENCES TABLE_COUNTRY(id),"
                    + "FOREIGN KEY(q5) REFERENCES TABLE_COUNTRY(id),"
                    + "FOREIGN KEY(q6) REFERENCES TABLE_COUNTRY(id)"
                    + ")";



    private static QuizDBHelper helperInstance;


    private QuizDBHelper( Context context ) {
        super( context, DB_NAME, null, DATABASE_VERSION );
    }


    public static synchronized QuizDBHelper getInstance(Context context) {
        // check if the instance already exists and if not, create the instance
        if( helperInstance == null ) {
            helperInstance = new QuizDBHelper( context.getApplicationContext() );
        }
        return helperInstance;
    }





    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE_COUNTRY);
        db.execSQL(CREATE_TABLE_RESULT);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("drop table if exists " + TABLE_COUNTRY);
        db.execSQL("drop table if exists " + TABLE_RESULT);

    }






}
