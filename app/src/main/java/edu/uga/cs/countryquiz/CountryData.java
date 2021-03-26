package edu.uga.cs.countryquiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class CountryData {

    //Private members
    private SQLiteDatabase db;
    private SQLiteOpenHelper geographyQuestionsDbHelper;

    public CountryData(Context context){
        this.geographyQuestionsDbHelper = QuizDBHelper.getInstance(context);
    }


    public void open(){
        db = geographyQuestionsDbHelper.getWritableDatabase();
    }


    public void close(){
        if (geographyQuestionsDbHelper != null){
            geographyQuestionsDbHelper.close();
        }
    }


    public Country storeGeographyQuestion(Country geographyQuestion){


        ContentValues values = new ContentValues();

        values.put(QuizDBHelper.GEOGRAPHYQUESTIONS_COLUMN_COUNTRY, geographyQuestion.getCountry());
        values.put(QuizDBHelper.GEOGRAPHYQUESTIONS_COLUMN_CONTINENT, geographyQuestion.getContinent());


        long id = db.insert(QuizDBHelper.TABLE_GEOGRAPHYQUESTIONS,null, values);

        geographyQuestion.setId(id);

        return geographyQuestion;
    }






}