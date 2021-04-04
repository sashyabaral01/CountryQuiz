package edu.uga.cs.countryquiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

/***
 * This class represents the quiz results page on the app, where users can see results of past quizzes
 */
public class ReviewQuizResults extends AppCompatActivity {

    //recycler view
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter recyclerAdapter;

    private QuizData quizData = null;
    private List<Quiz> quizList;

    /***
     * On create we need to set the visuals and begin the loading of data
     * @param savedInstanceState
     */
    @Override
    protected void onCreate( Bundle savedInstanceState ) {

        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_review_quiz_results );

        //find views
        recyclerView = (RecyclerView) findViewById( R.id.recycler_view );

        // use a linear layout manager for the recycler view
        layoutManager = new LinearLayoutManager(this );
        recyclerView.setLayoutManager( layoutManager );

        // Create a quiz dara
        quizData = new QuizData( this );

        //retrive quiz data
        new QuizResultDBReaderTask().execute();

    }

    /***
     * This class is made of reading the saved quiz results in the database asynchronously
     */
    private class QuizResultDBReaderTask extends AsyncTask<Void, Void, List<Quiz>> {


        /***
         * Background task of retrieving quiz data
         * @param params parameters
         * @return quiz data
         */
        @Override
        protected List<Quiz> doInBackground( Void... params ) {
            quizData.open();

            quizList = quizData.retrieveAllQuizzes();

            return quizList;
        }

        /***
         * Add to recycler view
         * @param jobLeadsList data
         */
        @Override
        protected void onPostExecute( List<Quiz> jobLeadsList ) {
            super.onPostExecute(jobLeadsList);
            recyclerAdapter = new QuizResultRecyclerAdapter( jobLeadsList );
            recyclerView.setAdapter( recyclerAdapter );
        }
    }

    /***
     * On resume we need to open the data if it was close
     */
    @Override
    protected void onResume() {
        // open the database in onResume
        if( quizData != null )
            quizData.open();
        super.onResume();
    }

    /***
     * on pause, close the data
     */
    @Override
    protected void onPause() {
        // close the database in onPause
        if( quizData != null )
            quizData.close();
        super.onPause();
    }
}