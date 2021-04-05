package edu.uga.cs.countryquiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

/***
 * The questions class is the controller for the quizing portion of the app.
 */
public class Questions extends AppCompatActivity {

    //constants
    private static final int QUESTION_COUNT = 6;

    //db vars
    QuizData quizData;
    CountryData countryData;
    SectionsPagerAdapter mSectionsPagerAdapter;
    List<Country> questionsData;

    //activity view vars
    ViewPager mViewPager;
    TabLayout tabLayout;
    PlaceholderFragment[] pageFrags = new PlaceholderFragment[QUESTION_COUNT + 1];

    /***
     * Oncreate, open the db and opena  quiz.
     * @param savedInstanceState saved data
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //get data from db
        countryData = new CountryData(this);
        quizData = new QuizData(this);
        quizData.open();
        countryData.open();
        new RetrieveRandomQuestion().execute();
        System.out.println(questionsData);

        //create layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        //add listeners onto the pager
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        tabLayout.setTabsFromPagerAdapter(mSectionsPagerAdapter);
        tabLayout.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        mViewPager.setOffscreenPageLimit(10);


    }

    /***
     * Load the question data into a page's fragment.
     * @param frag the actual fragment
     * @param countryText country text object
     * @param radio1 option 1
     * @param radio2 option 2
     * @param radio3 option 3
     * @param questionNumber question number in quiz
     */
    public void loadQuestionView(PlaceholderFragment frag, TextView countryText, RadioButton radio1, RadioButton radio2, RadioButton radio3, int questionNumber) {

        //save frag
        pageFrags[questionNumber -1] = frag;

        //set frag view to reflect the question
        Country currentCountry = questionsData.get(questionNumber - 1);
        countryText.setText(currentCountry.getCountry());
        ArrayList<String> options = quizData.getChoices(currentCountry.getContinent());
        radio1.setText("A: " +options.get(0));
        radio2.setText("B: " +options.get(1));
        radio3.setText("C: " +options.get(2));

    }

    /***
     * Load results fragment in pager
     * @param frag
     */
    public void loadResultsView(PlaceholderFragment frag) {

        //save frag for the end screen
        pageFrags[QUESTION_COUNT] = frag;

    }

    /***
     * Grade the quiz.
     * @return The number of correct answers (-1 if not finished)
     */
    public int grade(){

        //vars for grading
        int correctCount = 0;
        boolean unansweredQuestionExists = false;

        //loop through questions and count the correct ones
        for (int x = 0; x < QUESTION_COUNT; x++){

            //get radio buttons
            int radioButtonID = pageFrags[x].radioGroup.getCheckedRadioButtonId();

            //if one is selected
            if (radioButtonID != -1) {

                //check if its correct
                RadioButton radioButton = pageFrags[x].radioGroup.findViewById(radioButtonID);
                boolean correct = questionsData.get(x).getContinent().equals(radioButton.getText().toString().substring(3));
                //Log.e("Grade", pageFrags[x].questionNum + " - " + radioButton.getText() + " - " + correct);
                if (correct) correctCount++;
            }

            //if none are selected
            else {
                //Log.e("Grade", pageFrags[x].questionNum + " - ");
                unansweredQuestionExists = true;
            }
        }

        //set visuals for final screen
        //if the quiz wasnt finished
        if (unansweredQuestionExists){

            //set text
            pageFrags[QUESTION_COUNT].resultsMainText.setText("Not Finished");
            pageFrags[QUESTION_COUNT].resultsSubText.setText("Please return and answer all questions.");

            //return
            return -1;

        }
        //if the quiz was finished
        else {

            //set text
            pageFrags[QUESTION_COUNT].resultsMainText.setText(correctCount + "/" + QUESTION_COUNT);
            pageFrags[QUESTION_COUNT].resultsSubText.setText("Please return to the main menu to start a new quiz.");

            //loop through question and lock the radio buttons, and set the answer text
            for (int x = 0; x < QUESTION_COUNT; x++){

                //check if correct
                int radioButtonID = pageFrags[x].radioGroup.getCheckedRadioButtonId();
                RadioButton radioButton = pageFrags[x].radioGroup.findViewById(radioButtonID);
                boolean correct = questionsData.get(x).getContinent().equals(radioButton.getText().toString().substring(3));

                //lock buttons
                pageFrags[x].radioBtn1.setClickable(false);
                pageFrags[x].radioBtn2.setClickable(false);
                pageFrags[x].radioBtn3.setClickable(false);

                //set answer text
                if (correct) {
                    pageFrags[x].questionResultText.setText("CORRECT!");
                    pageFrags[x].questionResultText.setTextColor(0xFF4CAF50);
                }
                else {
                    pageFrags[x].questionResultText.setText("FALSE\nAnswer: " + questionsData.get(x).getContinent());
                    pageFrags[x].questionResultText.setTextColor(0xFFE91E63);
                }

            }

            //return
            return correctCount;

        }

    }

    /***
     * Class that defines the pager functionality
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        /***
         * Constructor
         * @param fm fragment manager
         */
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        //vars
        int currentPageIndex = 0;

        /***
         * Get the fragment on the page
         * @param position position of page
         * @return fragment found at position
         */
        @Override
        public Fragment getItem(int position) {

            // make frag
            return PlaceholderFragment.newInstance(position + 1);

        }

        /***
         * Number of pages in the page
         * @return count of pages
         */
        @Override
        public int getCount() {

            // Show 1 + number of questions pages.
            return QUESTION_COUNT + 1;

        }

        /***
         * Get page title in pager
         * @param position position
         * @return string title
         */
        @Override
        public CharSequence getPageTitle(int position) {

            //set the title
            int posNumber = position + 1;
            if (posNumber < (QUESTION_COUNT + 1)) return String.valueOf("Q" + posNumber);
            else return "END";

        }

        /***
         * Detect when a swipe happens. When the user swipes to the end screen, try to grade the quiz.
         * @param container view group
         * @param position position swiped to
         * @param object object
         */
        @Override
        public void setPrimaryItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            super.setPrimaryItem(container, position, object);

            //event on when you switch to a new page
            if (currentPageIndex != position){
                currentPageIndex = position;
                //Log.e("Pager", "Slide Event, on page " + (currentPageIndex + 1));

                //if entering the end page, grade
                if (currentPageIndex == QUESTION_COUNT) {

                    Quiz quiz=null;


                    //grade quiz
                    int correctCount = grade();

                    //if quiz is finished, save
                    if (correctCount != -1){
                        Date date = new Date();
                        // display time and date using toString()
                        //System.out.println(date.toString());
                        quiz = new Quiz(correctCount, date.toString());
                        new StoreQuizAsyncTask().execute(quiz);

                    }

                }

            }

        }

    }

    /***
     * Fragment for each page in the quiz layout
     */
    public static class PlaceholderFragment extends Fragment {

        //vars
        private static final String ARG_SECTION_NUMBER = "section_number";
        private int questionNum;
        private boolean isResultsScreen = false;

        //question scene vars
        private TextView countryText;
        private TextView questionResultText;
        private RadioButton radioBtn1;
        private RadioButton radioBtn2;
        private RadioButton radioBtn3;
        private RadioGroup radioGroup;

        //results scene vars
        private TextView resultsMainText;
        private TextView resultsSubText;

        /***
         * Constructor
         */
        public PlaceholderFragment() { }

        /***
         * Instantiate the frag
         * @param sectionNumber number of frag
         * @return the frag
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {

            //create fragment
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;

        }

        /***
         * Oncreate, set local vars
         * @param savedInstanceState saved data
         */
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            //set question number and determine if its the results screen
            if (getArguments() != null) {
                questionNum = getArguments().getInt(ARG_SECTION_NUMBER);
                if (questionNum == QUESTION_COUNT+1) isResultsScreen = true;
            } else {
                questionNum = -1;
            }

        }


        /***
         * Get views oncreateview
         * @param inflater inflater
         * @param container container
         * @param savedInstanceState saved data
         * @return view
         */
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            //Get views for the fragment
            View rootView;
            //if question
            if (!isResultsScreen) {
                rootView = inflater.inflate(R.layout.fragment_question, container, false);
                countryText = (TextView) rootView.findViewById(R.id.question_country);
                questionResultText = (TextView) rootView.findViewById(R.id.questions_result_text);
                radioBtn1 = (RadioButton) rootView.findViewById(R.id.question_answer_1);
                radioBtn2 = (RadioButton) rootView.findViewById(R.id.question_answer_2);
                radioBtn3 = (RadioButton) rootView.findViewById(R.id.question_answer_3);
                radioGroup = (RadioGroup) rootView.findViewById(R.id.questions_radio_group);
            }
            //if results screen
            else {
                rootView = inflater.inflate(R.layout.fragment_end, container, false);
                resultsMainText =  (TextView) rootView.findViewById(R.id.end_main_text);
                resultsSubText = (TextView) rootView.findViewById(R.id.end_sub_text);
                Button exitButton = (Button)rootView.findViewById(R.id.results_exit_button);
                exitButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(((Questions) getActivity()),MainActivity.class);
                        startActivity(intent);
                    }
                });
            }
            return rootView;

        }

        /***
         * Set the visual data in the fragment view
         * @param savedInstanceState saved data
         */
        @Override
        public void onActivityCreated(Bundle savedInstanceState) {

            super.onActivityCreated(savedInstanceState);

            //Load view for the fragment
            if (Questions.class.isInstance(getActivity())) {
                if (!isResultsScreen) ((Questions) getActivity()).loadQuestionView(this, countryText, radioBtn1, radioBtn2, radioBtn3, questionNum);
                else ((Questions) getActivity()).loadResultsView(this);
            }

        }
    }

    /***
     * Stores the quiz result in an asynchronous matter
     */
    private class StoreQuizAsyncTask extends AsyncTask<Quiz, Void, Void> {
        // in the onCreateMethod
        @Override
        protected Void doInBackground(Quiz ... quizzes) {
            quizData.open();
            quizData.storeResultsandDate(quizzes[0]);
            //Log.d(DEBUG_TAG, "storeResult: quiz result stored ");
            return null;
        }
    }

    /***
     * Retrieves a random questions
     */
    private class RetrieveRandomQuestion extends AsyncTask<Void, Void, List<Country> > {
        @Override
        protected List<Country> doInBackground(Void... params) {
            questionsData =  quizData.convertQuizIdToQuestions(quizData.retrieveRecentRow());
            return questionsData;
        }


        // in the onCreateMethod

    }

}