package edu.uga.cs.countryquiz;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.opencsv.CSVReader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
public class MainActivity extends AppCompatActivity {
    private static final String DEBUG = "Testing";
    private CountryData countryData = null;
    private QuizData quizData = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button startQuiz = findViewById(R.id.quiz_button);
        Button viewResults = findViewById(R.id.results_button);
        countryData = new CountryData(this);
        Quiz quiz = null;
        quizData = new QuizData(this);
        readFromCSV();
        startQuiz.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               quizData.open();
               int results = 6;
               String date = "Monday";
               Quiz quiz1 = new Quiz(results,date);
               quizData.storeResultsandDate(quiz1);
               System.out.println("Saved date");
               List<Long> questionById = countryData.retrieveAllQuestionsById();
               List<Long> quizDataList = quizData.retrieveRandomQuestionsById(questionById);
               Long q1 = quizDataList.get(0);
               Long q2 = quizDataList.get(1);
               Long q3 = quizDataList.get(2);
               Long q4 = quizDataList.get(3);
               Long q5 = quizDataList.get(4);
               Long q6 = quizDataList.get(5);


               Quiz quiz = new Quiz(q1,q2,q3,q4,q5,q6);
               quizData.storeGeographyQuiz(quiz);

               Quiz quiz2 = new Quiz(12,"Tuesday");
               Intent intent = new Intent(MainActivity.this,Questions.class);
               startActivity(intent);
           }
        });
        viewResults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this,ReviewQuizResults.class);
                startActivity(intent);
            }
        });
    }
    /*
    Storing the data asynchronously from the CSV to the SQLiteDB
     */
    private void readFromCSV() {
        countryData.open();
        List<Country> questionList = countryData.retrieveGeographyQuestions();
        if (questionList.size() == 0) {
            try {
                Resources res = getResources();
                InputStream in_s = res.openRawResource(R.raw.country_continent);
                CSVReader reader = new CSVReader(new InputStreamReader(in_s));
                String[] nextLine;
                while ((nextLine = reader.readNext()) != null) {
                    Country country1 = new Country(nextLine[0], nextLine[1]);
                    new CountryDBWriterTask().execute(country1);
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        else{
            System.out.println("Data is already filled");
        }
    }
    private class CountryDBWriterTask extends AsyncTask<Country, Void, Country> {
        @Override
        protected Country doInBackground(Country... country) {
            countryData.storeGeographyQuestion(country[0]);
            return country[0];
        }
        @Override
        protected void onPostExecute( Country country ) {
            super.onPostExecute( country );
        }
    }

    private class RetrieveRandomDBWriterTask extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {




            return null;
        }
    }
    @Override
    public void onResume() {
        // open the database in onResume
       if( countryData != null )
            countryData.open();
        super.onResume();
    }
    @Override
    public void onPause() {
        // close the database in onPause
        if( countryData != null )
            countryData.close();
        super.onPause();
    }
    }