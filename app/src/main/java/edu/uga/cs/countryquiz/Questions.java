package edu.uga.cs.countryquiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Questions extends AppCompatActivity {
    QuizData quizData;
    CountryData countryData;
    SectionsPagerAdapter mSectionsPagerAdapter;
    ViewPager mViewPager;
    TabLayout tabLayout;
    List<Country> questionsData;

    PlaceholderFragment[] pageFrags = new PlaceholderFragment[7];

    private static final String DEBUG_TAG = "Radio Button: ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        countryData = new CountryData(this);
        quizData = new QuizData(this);
        quizData.open();
        countryData.open();
        questionsData =  quizData.convertQuizIdToQuestions(quizData.retrieveRecentRow());
        System.out.println(questionsData);


       Country country = questionsData.get(2);//gets the 3rd country in the list
       System.out.println(country.getCountry() + " " + country.getContinent()); //prints that countries name and continent. 

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        tabLayout.setTabsFromPagerAdapter(mSectionsPagerAdapter);
        tabLayout.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        mViewPager.setOffscreenPageLimit(10);



    }
    public void loadQuestionView(PlaceholderFragment frag, TextView countryText, RadioButton radio1, RadioButton radio2, RadioButton radio3, int questionNumber) {
        pageFrags[questionNumber -1] = frag;
        Country currentCountry = questionsData.get(questionNumber - 1);
        countryText.setText(currentCountry.getCountry());
        ArrayList<String> options = quizData.getChoices(currentCountry.getContinent());
        radio1.setText(options.get(0));
        radio2.setText(options.get(1));
        radio3.setText(options.get(2));
    }

    public void loadResultsView(PlaceholderFragment frag) {
        pageFrags[6] = frag;
    }

    public void grade(){

        int correctCount = 0;
        boolean unansweredQuestionExists = false;

        for (int x = 0; x < 6; x++){
            int radioButtonID = pageFrags[x].radioGroup.getCheckedRadioButtonId();
            if (radioButtonID != -1) {

                RadioButton radioButton = pageFrags[x].radioGroup.findViewById(radioButtonID);
                boolean correct = questionsData.get(x).getContinent().equals(radioButton.getText());
                Log.e("Grade", pageFrags[x].questionNum + " - " + radioButton.getText() + " - " + correct);

                if (correct) correctCount++;
            }
            else {
                Log.e("Grade", pageFrags[x].questionNum + " - ");
                unansweredQuestionExists = true;
            }
        }

        if (unansweredQuestionExists){
            pageFrags[6].resultsMainText.setText("Not Finished");
            pageFrags[6].resultsSubText.setText("Please return and answer all questions");
        }
        else {
            pageFrags[6].resultsMainText.setText(correctCount + "/6");
            pageFrags[6].resultsSubText.setText("Good work");

            for (int x = 0; x < 6; x++){
                int radioButtonID = pageFrags[x].radioGroup.getCheckedRadioButtonId();
                RadioButton radioButton = pageFrags[x].radioGroup.findViewById(radioButtonID);
                boolean correct = questionsData.get(x).getContinent().equals(radioButton.getText());

                pageFrags[x].radioBtn1.setClickable(false);
                pageFrags[x].radioBtn2.setClickable(false);
                pageFrags[x].radioBtn3.setClickable(false);

                if (correct) {

                    pageFrags[x].questionResultText.setText("CORRECT!");
                    pageFrags[x].questionResultText.setTextColor(0xFF4CAF50);

                }
                else {

                    pageFrags[x].questionResultText.setText("FALSE\nCorrect: " + questionsData.get(x).getContinent());
                    pageFrags[x].questionResultText.setTextColor(0xFFE91E63);

                }
            }

        }

    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        int currentPageIndex = 0;

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }
        @Override
        public int getCount() {
            // Show 7 total pages.
            return 7;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            int posNumber = position + 1;
            if (posNumber < 7) return String.valueOf("Q" + posNumber);
            else return "END";

        }


        @Override
        public void setPrimaryItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            super.setPrimaryItem(container, position, object);

            if (currentPageIndex != position){
                currentPageIndex = position;
                Log.e("Pager", "Slide Event, on page " + (currentPageIndex + 1));


                //if entering the end page, grade
                if (currentPageIndex == 6) grade();

                //save quiz state

            }

        }
    }
    public static class PlaceholderFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";
        private int questionNum;
        private TextView countryText;
        private TextView questionResultText;
        private RadioButton radioBtn1;
        private RadioButton radioBtn2;
        private RadioButton radioBtn3;
        private RadioGroup radioGroup;

        private TextView resultsMainText;
        private TextView resultsSubText;

        private boolean isResultsScreen = false;

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);

            return fragment;
        }



        public PlaceholderFragment() {

        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            if (getArguments() != null) {
                questionNum = getArguments().getInt(ARG_SECTION_NUMBER);
                if (questionNum == 7) isResultsScreen = true;
            } else {
                questionNum = -1;
            }
        }



        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView;
            if (!isResultsScreen) {
                rootView = inflater.inflate(R.layout.fragment_question, container, false);
                countryText = (TextView) rootView.findViewById(R.id.question_country);
                questionResultText = (TextView) rootView.findViewById(R.id.questions_result_text);
                radioBtn1 = (RadioButton) rootView.findViewById(R.id.question_answer_1);
                radioBtn2 = (RadioButton) rootView.findViewById(R.id.question_answer_2);
                radioBtn3 = (RadioButton) rootView.findViewById(R.id.question_answer_3);
                radioGroup = (RadioGroup) rootView.findViewById(R.id.questions_radio_group);
            }
            else {
                rootView = inflater.inflate(R.layout.fragment_end, container, false);
                resultsMainText = (TextView) rootView.findViewById(R.id.end_main_text);
                resultsSubText = (TextView) rootView.findViewById(R.id.end_sub_text);
            }

            return rootView;
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            if (Questions.class.isInstance(getActivity())) {
                if (!isResultsScreen) ((Questions) getActivity()).loadQuestionView(this, countryText, radioBtn1, radioBtn2, radioBtn3, questionNum);
                else ((Questions) getActivity()).loadResultsView(this);
            }
        }
    }
}