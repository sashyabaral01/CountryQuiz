package edu.uga.cs.countryquiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

public class ReviewQuizResults extends AppCompatActivity {

    //recycler view
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter recyclerAdapter;

    private QuizData quizData = null;
    private List<Quiz> quizList;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {

        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_review_quiz_results );

        recyclerView = (RecyclerView) findViewById( R.id.recycler_view );

        // use a linear layout manager for the recycler view
        layoutManager = new LinearLayoutManager(this );
        recyclerView.setLayoutManager( layoutManager );

        // Create a JobLeadsData instance, since we will need to save a new JobLead to the dn.
        // Note that even though more activites may create their own instances of the JobLeadsData
        // class, we will be using a single instance of the JobLeadsDBHelper object, since
        // that class is a singleton class.
        quizData = new QuizData( this );

        // Execute the retrieval of the job leads in an asynchronous way,
        // without blocking the UI thread.

        //new QuizResultDBReaderTask().execute();

    }

    // This is an AsyncTask class (it extends AsyncTask) to perform DB reading of job leads, asynchronously.
    private class QuizResultDBReaderTask extends AsyncTask<Void, Void, List<Quiz>> {

        // This method will run as a background process to read from db.
        // It returns a list of retrieved JobLead objects.
        // It will be automatically invoked by Android, when we call the execute method
        // in the onCreate callback (the job leads review activity is started).
        @Override
        protected List<Quiz> doInBackground( Void... params ) {
            quizData.open();

            //quizList = quizData.retrieveAllJobLeads();

            return quizList;
        }

        // This method will be automatically called by Android once the db reading
        // background process is finished.  It will then create and set an adapter to provide
        // values for the RecyclerView.
        // onPostExecute is like the notify method in an asynchronous method call discussed in class.
        @Override
        protected void onPostExecute( List<Quiz> jobLeadsList ) {
            super.onPostExecute(jobLeadsList);
            recyclerAdapter = new QuizResultRecyclerAdapter( jobLeadsList );
            recyclerView.setAdapter( recyclerAdapter );
        }
    }

    @Override
    protected void onResume() {
        // open the database in onResume
        if( quizData != null )
            quizData.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        // close the database in onPause
        if( quizData != null )
            quizData.close();
        super.onPause();
    }
}