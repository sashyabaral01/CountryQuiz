package edu.uga.cs.countryquiz;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * This is an adapter class for the RecyclerView to show all job leads.
 */
public class QuizResultRecyclerAdapter extends RecyclerView.Adapter<QuizResultRecyclerAdapter.QuizResultHolder> {


    private List<Quiz> quizList;

    public QuizResultRecyclerAdapter( List<Quiz> quizList ) {
        this.quizList = quizList;
    }

    // The adapter must have a ViewHolder class to "hold" one item to show.
    class QuizResultHolder extends RecyclerView.ViewHolder {

        TextView dateText;
        TextView scoreText;

        public QuizResultHolder(View itemView ) {
            super(itemView);

            dateText = itemView.findViewById( R.id.quiz_result_date);
            scoreText = itemView.findViewById( R.id.quiz_result_score);
        }
    }

    @Override
    public QuizResultHolder onCreateViewHolder( ViewGroup parent, int viewType ) {
        // We need to make sure that all CardViews have the same, full width, allowed by the parent view.
        // This is a bit tricky, and we must provide the parent reference (the second param of inflate)
        // and false as the third parameter (don't attach to root).
        // Consequently, the parent view's (the RecyclerView) width will be used (match_parent).
        View view = LayoutInflater.from( parent.getContext()).inflate( R.layout.quiz_result, parent, false );
        return new QuizResultHolder( view );
    }

    // This method fills in the values of a holder to show a JobLead.
    // The position parameter indicates the position on the list of jobs list.
    @Override
    public void onBindViewHolder( QuizResultHolder holder, int position ) {
        Quiz quiz = quizList.get( position );


        System.out.println(quizList.get(position));

        if(quiz.getDate()!=null && quiz.getResult()!=0) {
            System.out.println("The date is  null");

            holder.dateText.setText("Date: " + quiz.getDate());
            holder.scoreText.setText("Score: " + String.valueOf(quiz.getResult()));
        }
    }

    @Override
    public int getItemCount() {
        return quizList.size();
    }
}
