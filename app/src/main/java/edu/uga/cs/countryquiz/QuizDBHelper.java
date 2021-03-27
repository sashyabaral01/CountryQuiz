package edu.uga.cs.countryquiz;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.RadioGroup;

/**
 * This class is the Database helper, where the DB Tables are created and
 * upgraded.
 * */
public class QuizDBHelper extends SQLiteOpenHelper {

    //Constant values for easier code reading
    private static final String DB_NAME = "test3.db";
    //Current version of the DB (Increment to rebuild database)
    // -- DO NOT DECREMENT --
    private static final int DB_VERSION = 1;

    //Name of the Geography Quizzes Table


    //Name of the Questions Table
    public static final String TABLE_GEOGRAPHYQUESTIONS = "geographyquestions";
    //Name of the columns for the Geography Quizzes Table
    public static final String GEOGRAPHYQUESTIONS_COLUMN_ID = "id";
    public static final String GEOGRAPHYQUESTIONS_COLUMN_CONTINENT = "continent";
    public static final String GEOGRAPHYQUESTIONS_COLUMN_COUNTRY = "country";




    //Creating Geography Questions Table
    private static final String CREATE_GEOGRAPHYQUESTIONS =
            "CREATE TABLE " + TABLE_GEOGRAPHYQUESTIONS + "("
                    + GEOGRAPHYQUESTIONS_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + GEOGRAPHYQUESTIONS_COLUMN_CONTINENT + " TEXT,"
                    + GEOGRAPHYQUESTIONS_COLUMN_COUNTRY + " TEXT"
                    + ")";

    //Private reference to the single instance
    private static QuizDBHelper helperInstance;


    private QuizDBHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }


    public static synchronized QuizDBHelper getInstance(Context context){
        //If DB has not been instantiated
        if (helperInstance == null){
            //Instantiate DB
            helperInstance = new QuizDBHelper(context);
        }
        //Return instance of the DB
        return helperInstance;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("CUSTOM", "DBHelper onCreate called");
        db.execSQL(CREATE_GEOGRAPHYQUESTIONS);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop tables and recreate them if the version has changed
        db.execSQL("drop table if exists " + TABLE_GEOGRAPHYQUESTIONS);

        onCreate(db);
    }




}