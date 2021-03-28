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
        ArrayList<Country> questions = new ArrayList<>();
        Cursor cursor = null;

        try {
            // Execute the select query and get the Cursor to iterate over the retrieved rows
            cursor = db.query( QuizDBHelper.TABLE_GEOGRAPHYQUESTIONS, allColumns,
                    null, null, null, null, null );
            // collect all questions into a List
            if( cursor.getCount() > 0 ) {
                while( cursor.moveToNext() ) {
                    // get all attribute values of this question
                    long id = cursor.getInt( cursor.getColumnIndex( QuizDBHelper.GEOGRAPHYQUESTIONS_COLUMN_ID ) );
                    String country = cursor.getString( cursor.getColumnIndex( QuizDBHelper.GEOGRAPHYQUESTIONS_COLUMN_COUNTRY ) );
                    String continent = cursor.getString( cursor.getColumnIndex( QuizDBHelper.GEOGRAPHYQUESTIONS_COLUMN_CONTINENT ));
                    Country country1 = new Country(country,continent);
                    country1.setId(id);

                    questions.add(country1);
                }
            }
            Log.d( DEBUG_TAG, "retrieveAllQuestions - Number of records from DB: " + cursor.getCount() );
        }
        catch( Exception e ){
            Log.d( DEBUG_TAG, "Exception caught: " + e );
        }
        finally{
            // we should close the cursor
            if (cursor != null) {
                cursor.close();
            }
        }
        return questions;
    }
    public Country storeGeographyQuestion(Country geographyQuestion){
        ContentValues values = new ContentValues();
        values.put(QuizDBHelper.GEOGRAPHYQUESTIONS_COLUMN_COUNTRY, geographyQuestion.getCountry());
        values.put(QuizDBHelper.GEOGRAPHYQUESTIONS_COLUMN_CONTINENT, geographyQuestion.getContinent());
        long id = db.insert(QuizDBHelper.TABLE_GEOGRAPHYQUESTIONS,null, values);
        geographyQuestion.setId(id);
        return geographyQuestion;
    }




    public List<Long> retrieveAllQuestionsById() {


        ArrayList<Long> questionsById = new ArrayList<>();
        Cursor cursor = null;

        try {
            // Execute the select query and get the Cursor to iterate over the retrieved rows
            cursor = db.query( QuizDBHelper.TABLE_GEOGRAPHYQUESTIONS, allColumns,
                    null, null, null, null, null );
            // collect all questions into a List
            if( cursor.getCount() > 0 ) {
                while( cursor.moveToNext() ) {
                    // get all attribute values of this question
                    long id = cursor.getInt( cursor.getColumnIndex( QuizDBHelper.GEOGRAPHYQUESTIONS_COLUMN_ID ) );
                    String country = cursor.getString( cursor.getColumnIndex( QuizDBHelper.GEOGRAPHYQUESTIONS_COLUMN_COUNTRY ) );
                    String continent = cursor.getString( cursor.getColumnIndex( QuizDBHelper.GEOGRAPHYQUESTIONS_COLUMN_CONTINENT ));
                    Country country1 = new Country(country,continent);
                    country1.setId(id);

                    questionsById.add(country1.getId());
                }
            }
            Log.d( DEBUG_TAG, "retrieveAllQuestions - Number of records from DB: " + cursor.getCount() );
        }
        catch( Exception e ){
            Log.d( DEBUG_TAG, "Exception caught: " + e );
        }
        finally{
            // we should close the cursor
            if (cursor != null) {
                cursor.close();
            }
        }


        return questionsById;
    }




}