package edu.uga.cs.countryquiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class QuizData {
    private static final String DEBUG_TAG = "Test: ";
    private QuizData quizData;
    //Private members
    private SQLiteDatabase db;
    private SQLiteOpenHelper geographyQuizDbHelper;
    private static final String[] allColumns = {
            QuizDBHelper.GEOGRAPHYQUIZZES_COLUMN_ID,
            QuizDBHelper.GEOGRAPHYQUIZZES_COLUMN_DATE,
            QuizDBHelper.GEOGRAPHYQUIZZES_COLUMN_Q1,
            QuizDBHelper.GEOGRAPHYQUIZZES_COLUMN_Q2,
            QuizDBHelper.GEOGRAPHYQUIZZES_COLUMN_Q3,
            QuizDBHelper.GEOGRAPHYQUIZZES_COLUMN_Q4,
            QuizDBHelper.GEOGRAPHYQUIZZES_COLUMN_Q5,
            QuizDBHelper.GEOGRAPHYQUIZZES_COLUMN_Q6,
            QuizDBHelper.GEOGRAPHYQUIZZES_COLUMN_RESULTS
    };
    public QuizData(Context context) {
        this.geographyQuizDbHelper = QuizDBHelper.getInstance(context);
    }
    public void generateRandomQuizzes(Quiz quiz){
        ContentValues values = new ContentValues();
        values.put(QuizDBHelper.GEOGRAPHYQUIZZES_COLUMN_Q1, quiz.getQuestion1());
        System.out.println("From QuizData: " +  quiz.getQuestion1());
        values.put(QuizDBHelper.GEOGRAPHYQUIZZES_COLUMN_Q2, quiz.getQuestion2());
        values.put(QuizDBHelper.GEOGRAPHYQUIZZES_COLUMN_Q3, quiz.getQuestion3());
        values.put(QuizDBHelper.GEOGRAPHYQUIZZES_COLUMN_Q4, quiz.getQuestion4());
        values.put(QuizDBHelper.GEOGRAPHYQUIZZES_COLUMN_Q5, quiz.getQuestion5());
        values.put(QuizDBHelper.GEOGRAPHYQUIZZES_COLUMN_Q6, quiz.getQuestion6());
        long id = db.insert(QuizDBHelper.TABLE_GEOGRAPHYQUIZZES,null, values);
        quiz.setId(id);
    }



    public void storeResultsandDate(Quiz quiz){

        String query = "UPDATE geographyquizzes set Results = " + quiz.getResult() + ", " + "Date = '" +quiz.getDate()   + "' WHERE id = (SELECT MAX(id) FROM geographyquizzes)";
        System.out.println("Query : " + query);
        db.execSQL(query);

    }

    public List<Long> retrieveRandomQuestionsById(List<Long> countryListId){
        List<Long> randomQuestionsId = new ArrayList<>();
        List<Long> shuffledListQuestions = new ArrayList<>();
        for(int i =0; i<195; i++) {
            randomQuestionsId.add(countryListId.get(i));
        }
        Collections.shuffle(randomQuestionsId);

        for(int i =0;i<6;i++){
            shuffledListQuestions.add(randomQuestionsId.get(i));
        }
        return shuffledListQuestions;
    }


    public List<Long> retrieveRecentRow(){
        List<Long> recentRow = new ArrayList<>();
        Quiz quiz1 = null;
        String test = "select * from GEOGRAPHYQUIZZES where id = (select max(id) from GEOGRAPHYQUIZZES)";
        Cursor cursor = db.rawQuery(test, null);
        cursor.moveToLast();
        long id = cursor.getInt( cursor.getColumnIndex( QuizDBHelper.GEOGRAPHYQUIZZES_COLUMN_ID ) );
        Long qu1 = cursor.getLong( cursor.getColumnIndex( QuizDBHelper.GEOGRAPHYQUIZZES_COLUMN_Q1 ) );
        Long qu2 = cursor.getLong( cursor.getColumnIndex( QuizDBHelper.GEOGRAPHYQUIZZES_COLUMN_Q2 ) );
        Long qu3 = cursor.getLong( cursor.getColumnIndex( QuizDBHelper.GEOGRAPHYQUIZZES_COLUMN_Q3 ) );
        Long qu4 = cursor.getLong( cursor.getColumnIndex( QuizDBHelper.GEOGRAPHYQUIZZES_COLUMN_Q4 ) );
        Long qu5 = cursor.getLong( cursor.getColumnIndex( QuizDBHelper.GEOGRAPHYQUIZZES_COLUMN_Q5 ) );
        Long qu6 = cursor.getLong( cursor.getColumnIndex( QuizDBHelper.GEOGRAPHYQUIZZES_COLUMN_Q6 ) );
         quiz1 = new Quiz(qu1,qu2,qu3,qu4,qu5,qu6);
        quiz1.setId(id);
        recentRow.add(qu1);
        recentRow.add(qu2);
        recentRow.add(qu3);
        recentRow.add(qu4);
        recentRow.add(qu5);
        recentRow.add(qu6);

        System.out.println("From inside the method: " +recentRow);
        return recentRow;
    }

    public List<Country> convertQuizIdToQuestions(List<Long> ids){
        System.out.println("Entered function");
        List<Country> convertedQuestions = new ArrayList<>();
        Country c1;
        for(int i = 0; i<6; i++){
            String query = "SELECT country,continent  FROM GEOGRAPHYQUESTIONS where id = " + ids.get(i);
            System.out.println(query);
            Cursor c = db.rawQuery(query,null);
            c.moveToFirst();
            String continent =  c.getString( c.getColumnIndex( QuizDBHelper.GEOGRAPHYQUESTIONS_COLUMN_CONTINENT ) );
            String country =  c.getString( c.getColumnIndex( QuizDBHelper.GEOGRAPHYQUESTIONS_COLUMN_COUNTRY ) );
            c1 = new Country(country,continent);
            convertedQuestions.add(c1);
        }
        return convertedQuestions;
    }
    public void open(){
        db = geographyQuizDbHelper.getWritableDatabase();
    }
    //Close the database connection
    public void close(){
        if (geographyQuizDbHelper != null){
            geographyQuizDbHelper.close();
        }
    }





    public List<Quiz> retrieveAllQuizzes() {
        ArrayList<Quiz> quizzes = new ArrayList<>();
        Cursor cursor = null;

        try {
            // Execute the select query and get the Cursor to iterate over the retrieved rows
            cursor = db.query( QuizDBHelper.TABLE_GEOGRAPHYQUIZZES, allColumns,
                    null, null, null, null, null );

            // collect all job leads into a List
            if( cursor.getCount() > 0 ) {
                while( cursor.moveToNext() ) {
                    System.out.println("Entering the last function");
                    // get all attribute values of this job lead
                    long id = cursor.getLong( cursor.getColumnIndex( QuizDBHelper.GEOGRAPHYQUIZZES_COLUMN_ID ) );
                    String date = cursor.getString( cursor.getColumnIndex( QuizDBHelper.GEOGRAPHYQUIZZES_COLUMN_DATE ) );
                    int  result = cursor.getInt( cursor.getColumnIndex( QuizDBHelper.GEOGRAPHYQUIZZES_COLUMN_RESULTS ) );


                    Quiz quizInformation = new Quiz(result,date);
                    quizInformation.setId( id ); // set the id (the primary key) of this object
                    // add it to the list
                    quizzes.add( quizInformation );
                }
            }
            Log.d( DEBUG_TAG, "Number of records from DB: " + cursor.getCount() );
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
        // return a list of retrieved job leads
        return quizzes;
    }

    public ArrayList<String> getChoices(String correctContinent){


        ArrayList<String> choices = new ArrayList<>();

        ArrayList<String> radioTextArray = new ArrayList<>();
        choices.add("Africa");
        choices.add("Asia");
        choices.add("Europe");
        choices.add("North America");
        choices.add("South America");
        choices.add("Oceania");
        radioTextArray.add(correctContinent);
        choices.remove(correctContinent);
        int size = choices.size();
        Random random = new Random();
        int randomIndexOne = random.nextInt(choices.size());
        radioTextArray.add(choices.get(randomIndexOne));
        choices.remove(randomIndexOne);
        int randomIndexTwo = random.nextInt(choices.size());
        radioTextArray.add(choices.get(randomIndexTwo));
        choices.remove(randomIndexTwo);
        Collections.shuffle(radioTextArray);
        return radioTextArray;
    }


    public void deleteNullRows(){


        String query2 = "DELETE FROM GEOGRAPHYQUIZZES WHERE Date IS NULL";


        db.execSQL(query2);


    }
}
