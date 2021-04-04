package edu.uga.cs.countryquiz;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * This is an adapter class for the RecyclerView to show all quizzes
 */
public class QuizResultRecyclerAdapter extends RecyclerView.Adapter<QuizResultRecyclerAdapter.QuizResultHolder> {

    //list of quizes
    private List<Quiz> quizList;

    /***
     * Constructor
     * @param quizList data
     */
    public QuizResultRecyclerAdapter( List<Quiz> quizList ) {
        this.quizList = quizList;
    }

    /***
     * This class represents the "holder" on the recycler view
     */
    class QuizResultHolder extends RecyclerView.ViewHolder {

        TextView dateText;
        TextView scoreText;

        /***
         * Constructor
         * @param itemView view
         */
        public QuizResultHolder(View itemView ) {
            super(itemView);

            dateText = itemView.findViewById( R.id.quiz_result_date);
            scoreText = itemView.findViewById( R.id.quiz_result_score);
        }
    }

    /***
     * We need to inflate the view here
     * @param parent where to create the view
     * @param viewType view type
     * @return
     */
    @Override
    public QuizResultHolder onCreateViewHolder( ViewGroup parent, int viewType ) {
        View view = LayoutInflater.from( parent.getContext()).inflate( R.layout.quiz_result, parent, false );
        return new QuizResultHolder( view );
    }

    /***
     * Fill the values of the holder
     * @param holder holder to fill
     * @param position position in list
     */
    @Override
    public void onBindViewHolder( QuizResultHolder holder, int position ) {
        Quiz quiz = quizList.get( position );


        System.out.println(quizList.get(position));

        if(quiz.getDate()!=null) {
            System.out.println("The date is  null");

            holder.dateText.setText("Date: " + quiz.getDate());
            holder.scoreText.setText("Score: " + String.valueOf(quiz.getResult()) + "/6");
        }
    }

    /***
     * Ammount of quiz items
     * @return count
     */
    @Override
    public int getItemCount() {
        return quizList.size();
    }
}
