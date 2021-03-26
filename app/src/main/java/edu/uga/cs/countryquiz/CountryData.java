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

    private static final String DEBUG_TAG = "Test: ";
    //Private members
    private SQLiteDatabase db;
    private SQLiteOpenHelper geographyQuestionsDbHelper;

    private static final String[] allColumns = {
            QuizDBHelper.GEOGRAPHYQUESTIONS_COLUMN_ID,
            QuizDBHelper.GEOGRAPHYQUESTIONS_COLUMN_COUNTRY,
            QuizDBHelper.GEOGRAPHYQUESTIONS_COLUMN_CONTINENT
    };




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


    public List<Country> retrieveGeographyQuestions() {

        //Start reading from database
        db = geographyQuestionsDbHelper.getReadableDatabase();

        //ArrayList of geography questions
        ArrayList<Country> geographyQuestions = new ArrayList<>();
        Cursor cursor = null;

        cursor = db.query(QuizDBHelper.TABLE_GEOGRAPHYQUESTIONS, allColumns, null, null, null, null, null);
        while (cursor.moveToNext()) {
            //Query the values from one row, iterating over each row
            long id = cursor.getLong(cursor.getColumnIndex(QuizDBHelper.GEOGRAPHYQUESTIONS_COLUMN_ID));
            String continent = cursor.getString(cursor.getColumnIndex(QuizDBHelper.GEOGRAPHYQUESTIONS_COLUMN_CONTINENT));
            String country = cursor.getString(cursor.getColumnIndex(QuizDBHelper.GEOGRAPHYQUESTIONS_COLUMN_COUNTRY));

            //Create Geography Question object with current values
            Country geographyQuestion = new Country(continent, country);
            geographyQuestion.setId(id);

            //Append current row's data to the ArrayList
            geographyQuestions.add(geographyQuestion);
        }

        //Close cursor and return List of geography questions
        cursor.close();
        return geographyQuestions;

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