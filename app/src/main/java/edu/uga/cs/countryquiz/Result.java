package edu.uga.cs.countryquiz;

import java.time.LocalDate;

public class Result {

    int identifer;
    String question1;
    String question2;
    String question3;
    String question4;
    String question5;
    String question6;
    String date;

    public Result()
    {
        this.identifer = -1;
        this.question1 = null;
        this.question2 = null;
        this.question3 = null;
        this.question4 = null;
        this.question5 = null;
        this.question6=null;
        this.date = null;
    }


    public Result(int id, String question1, String question2, String question3, String question4, String question5, String question6) {
        this.identifer = id;
        this.question1 = question1;
        this.question2 = question2;
        this.question3 = question3;
        this.question4 = question4;
        this.question5 = question5;
        this.question6 = question6;
        this.date = LocalDate.now().toString();
    }




    public int getId() {
        return identifer;
    }

    public void setId(int id) {
        this.identifer = id;
    }

    public String getQuestion1() {
        return question1;
    }

    public void setQuestion1(String question1) {
        this.question1 = question1;
    }

    public String getQuestion2() {
        return question2;
    }

    public void setQuestion2(String question2) {
        this.question2 = question2;
    }

    public String getQuestion3() {
        return question3;
    }

    public void setQuestion3(String question3) {
        this.question3 = question3;
    }

    public String getQuestion4() {
        return question4;
    }

    public void setQuestion4(String question4) {
        this.question4 = question4;
    }

    public String getQuestion5() {
        return question5;
    }

    public void setQuestion5(String question5) {
        this.question5 = question5;
    }

    public String getQuestion6() {
        return question6;
    }

    public void setQuestion6(String question6) {
        this.question6 = question6;
    }


}
