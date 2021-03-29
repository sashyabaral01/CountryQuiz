package edu.uga.cs.countryquiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import java.util.List;

public class Questions extends AppCompatActivity {
    QuizData quizData;
    CountryData countryData;

    SectionsPagerAdapter mSectionsPagerAdapter;
    ViewPager mViewPager;
    TabLayout tabLayout;

    List<Country> questionsData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        countryData = new CountryData(this);
        quizData = new QuizData(this);
        quizData.open();
        countryData.open();



        questionsData =  quizData.convertQuizIdToQuestions(quizData.retrieveRecentRow());
        System.out.println(questionsData);
        //this has the random questions in an arraylist.
        // For example [Azerbaijan Europe, Singapore Asia, Djibouti Africa, Yemen Asia, Uzbekistan Asia, Switzerland Europe] 6 entries
        //this is sample output

        /*
        This is to test the retrieved countries. Trying to retrieve. Just a random index chosen
         */
        Country country= questionsData.get(2); //gets Djibouti Africa from above on line 40
        System.out.println("Get country: " +country.getCountry()); //prints Djibouti
        System.out.println("Get continent: " + country.getContinent()); //prints Africa


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);




        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        //toolbar.setTitle(mSectionsPagerAdapter.getPageTitle(0));
        mViewPager = (ViewPager) findViewById(R.id.pager);



        mViewPager.setAdapter(mSectionsPagerAdapter);
        tabLayout.setTabsFromPagerAdapter(mSectionsPagerAdapter);
        tabLayout.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));


    }

    public void loadView(TextView countryText, RadioButton radio1, RadioButton radio2, RadioButton radio3, int questionNumber) {
        Country currentCountry = questionsData.get(questionNumber - 1);
        countryText.setText(currentCountry.getCountry());
        radio1.setText(currentCountry.getContinent());
        radio2.setText("continent2");
        radio3.setText("continent3");
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

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_simple_tabs, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/
}