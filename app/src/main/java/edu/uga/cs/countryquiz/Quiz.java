package edu.uga.cs.countryquiz;
import java.time.LocalDate;
/*
** Pojo class for the quiz
 */
public class Quiz {
    long id;
    Long q1;
    Long q2;
    Long q3;
    Long q4;
    Long q5;
    Long q6;
    int result;
    String date;


    /***
     * Gets the result
     * @return
     */

    public int getResult() {
        return result;
    }


    /***
     * Sets the result
     * @param result
     */

    public void setResult(int result) {
        this.result = result;
    }


    /***
     * Gets the date
     * @return
     */

    public String getDate() {
        return date;
    }


    /***
     * Sets the date
     * @param date
     */
    public void setDate(String date) {
        this.date = date;
    }


    /***
     * Constructor for storing the question
     * @param q1
     * @param q2
     * @param q3
     * @param q4
     * @param q5
     * @param q6
     */
    public Quiz(Long q1, Long q2, Long q3, Long q4, Long q5, Long q6) {
        this.q1 = q1;
        this.q2 = q2;
        this.q3 = q3;
        this.q4 = q4;
        this.q5 = q5;
        this.q6 = q6;


    }

    /***
     * Stores the result and ate
     * @param result
     * @param date
     */

    public Quiz(int result, String date){
        this.result=result;
        this.date = date;
    }

    /***
     * Gets the id
     * @return
     */
    public Long getId() {
        return id;
    }

    /***
     * Sets the id
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }


    /***
     * Gets the first question
     * @return
     */
    public Long getQuestion1() {
        return q1;
    }

    /***
     * Sets the first question
     * @param question1
     */
    public void setQuestion1(Long question1) {
        this.q1 = question1;
    }

    /***
     * Gets the question
     * @return
     */
    public Long getQuestion2() {
        return q2;
    }

    /***
     * Sets the question
     * @param question2
     */
    public void setQuestion2(Long question2) {
        this.q2 = question2;
    }

    /***
     * Gets the third question
     * @return
     */
    public Long getQuestion3() {
        return q3;
    }

    /***
     * Sets the question
     * @param question3
     */
    public void setQuestion3(Long question3) {
        this.q3 = question3;
    }

    /***
     * Gets the question
     * @return
     */
    public Long getQuestion4() {
        return q4;
    }

    /***
     * Sets the question
     * @param question4
     */
    public void setQuestion4(Long question4) {
        this.q4 = question4;
    }

    /***
     * Gets the question
     * @return
     */
    public Long getQuestion5() {
        return q5;
    }

    /***
     * Sets the question five
     * @param question5
     */
    public void setQuestion5(Long question5) {
        this.q5 = question5;
    }

    /***
     * Gets question 6
     * @return
     */
    public Long getQuestion6() {
        return q6;
    }

    /***
     *Sets questions six
     * @param question6
     */
    public void setQuestion6(Long question6) {
        this.q6 = question6;
    }

    /***
     * Creates a string of the values
     * @return
     */
    @Override
    public String toString() {
        return q1+" "+q2+" "+q3+" " +q4+ " "+q5+" " +q6;
    }


}
