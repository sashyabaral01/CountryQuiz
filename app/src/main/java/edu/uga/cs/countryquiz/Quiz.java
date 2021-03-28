package edu.uga.cs.countryquiz;
import java.time.LocalDate;
public class Quiz {
    long id;

    Long q1;
    Long q2;
    Long q3;
    Long q4;
    Long q5;
    Long q6;

    public Quiz(Long q1, Long q2, Long q3, Long q4, Long q5, Long q6) {
        this.q1 = q1;
        this.q2 = q2;
        this.q3 = q3;
        this.q4 = q4;
        this.q5 = q5;
        this.q6 = q6;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getQuestion1() {
        return q1;
    }

    public void setQuestion1(Long question1) {
        this.q1 = question1;
    }

    public Long getQuestion2() {
        return q2;
    }

    public void setQuestion2(Long question2) {
        this.q2 = question2;
    }

    public Long getQuestion3() {
        return q3;
    }

    public void setQuestion3(Long question3) {
        this.q3 = question3;
    }

    public Long getQuestion4() {
        return q4;
    }

    public void setQuestion4(Long question4) {
        this.q4 = question4;
    }

    public Long getQuestion5() {
        return q5;
    }

    public void setQuestion5(Long question5) {
        this.q5 = question5;
    }

    public Long getQuestion6() {
        return q6;
    }

    public void setQuestion6(Long question6) {
        this.q6 = question6;
    }


    @Override
    public String toString() {

        return q1+" "+q2+" "+q3+" " +q4+ " "+q5+" " +q6;

    }


}
