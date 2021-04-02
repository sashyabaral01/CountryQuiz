package edu.uga.cs.countryquiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
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
    public void loadView(TextView countryText, RadioButton radio1, RadioButton radio2, RadioButton radio3, int questionNumber) {
        Country currentCountry = questionsData.get(questionNumber - 1);
        countryText.setText(currentCountry.getCountry());
       ArrayList<String> options = quizData.getChoices(currentCountry.getContinent());
        radio1.setText(options.get(0));
        radio2.setText(options.get(1));
        radio3.setText(options.get(2));
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }
        @Override
        public int getCount() {
            // Show 6 total pages.
            return 6;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            int imageNum = position + 1;
            return String.valueOf("Q" + imageNum);
        }
    }
    public static class PlaceholderFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";
        private int questionNum;
        private TextView countryText;
        private RadioButton radioBtn1;
        private RadioButton radioBtn2;
        private RadioButton radioBtn3;

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
            } else {
                questionNum = -1;
            }
        }



        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_question, container, false);
            countryText = (TextView) rootView.findViewById(R.id.question_country);
            radioBtn1 = (RadioButton) rootView.findViewById(R.id.question_answer_1);
            radioBtn2 = (RadioButton) rootView.findViewById(R.id.question_answer_2);
            radioBtn3 = (RadioButton) rootView.findViewById(R.id.question_answer_3);
            return rootView;
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            if (Questions.class.isInstance(getActivity())) {
                ((Questions) getActivity()).loadView(countryText, radioBtn1, radioBtn2, radioBtn3, questionNum);


            }
        }
    }
}